package com.example.javaserver.controller;


import com.example.javaserver.entity.ERole;
import com.example.javaserver.entity.RefreshToken;
import com.example.javaserver.entity.Role;
import com.example.javaserver.entity.User;
import com.example.javaserver.payload.request.*;
import com.example.javaserver.payload.response.JwtResponse;
import com.example.javaserver.payload.response.MessageResponse;
import com.example.javaserver.payload.response.RefreshTokenResponse;
import com.example.javaserver.respository.RoleRepository;
import com.example.javaserver.respository.UserRepository;
import com.example.javaserver.security.jwt.JwtUtils;
import com.example.javaserver.security.jwt.TokenRefreshException;
import com.example.javaserver.security.jwt.advice.BadRequestException;
import com.example.javaserver.service.EmailSenderService;
import com.example.javaserver.service.RefreshTokenService;
import com.example.javaserver.service.UserDetailsImpl;
import com.example.javaserver.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RefreshTokenService refreshTokenService;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getPassword() + "_" + loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        System.out.println("login:" + loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (userDetails.isActive()) {
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//            System.out.println("db: " + userDetails.getPassword());
//            System.out.println(refreshToken.getToken());
            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    refreshToken.getToken(),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getFullname(),
                    roles.get(0)));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: This Account is inactive "));
        }
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new RefreshTokenResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    //    @PostMapping("/changePassword/{id}")
//    public ResponseEntity<?> updatePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
//                                            @PathVariable("id") int id) {
//        if (!userRepository.existsById(id)) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: username done exists!"));
//        } else {
//            User userdb = userRepository.findUserById(id);// Create new user's account
//            if (encoder.matches(changePasswordRequest.getOldPassword(), userdb.getPassword())) {
//                System.out.println("new pass: " + changePasswordRequest.getNewPassword());
//                userdb.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
//                userRepository.save(userdb);
//                return ResponseEntity.ok(new MessageResponse("User updated password successfully!"));
//            } else
//                return ResponseEntity.badRequest().body(new MessageResponse("Old Password not true!"));
//        }
//    }
    @PostMapping("/resigter")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username already exists!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getFullname(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhone());

        // xet role
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        System.out.println(roles);
        if (strRoles == null) {
            Role userRole = roleRepository.findByRolename(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRolename(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByRolename(ERole.ROLE_MOD)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRolename(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> sendVerificationMail(final HttpServletRequest request, @Valid @RequestBody
            LoginRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByUsername(emailRequest.getUsername())) {
            User user = userRepository.findUserByUsername(emailRequest.getUsername());
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            emailSenderService.sendMail(getAppUrl(request), user.getUsername(), token);
            return ResponseEntity.ok(new MessageResponse("Verification link is sent on your mail id"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Email is not associated with any account"));
        }

    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    //    @GetMapping("confirm-account")
//    public ResponseEntity<?> getMethodName(@RequestParam("token") String token) {
//
//        User user = userService.getByResetPasswordToken(token);
//
//        if (user == null) {
//            throw new BadRequestException("Invalid token");
//        }
//
//        Calendar calendar = Calendar.getInstance();
//
//        if((user.getExpiryDate().getTime() -
//                calendar.getTime().getTime()) <= 0) {
//            return ResponseEntity.badRequest().body("Link expired. Generate new link from http://localhost:4200/login");
//        }
//
//        userRepository.save(user);
//        return ResponseEntity.ok("Account verified successfully!");
//    }
    @PostMapping("/vertifyResetToken")
    public ResponseEntity<?> vertifyResetToken(@Valid @RequestBody
                                                       String resetToken) {
        String result = userService.validatePasswordResetToken(resetToken);
        if (result != null) {
            if (result == "invalidToken") {
                return ResponseEntity.badRequest().body(new MessageResponse("Token is not found"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Token is expired"));
            }
        } else {
            return ResponseEntity.ok(new MessageResponse("token: is vertify"));
        }
    }

    @PostMapping("/newPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ChangePasswordRequest changePassword) {

        Optional<User> user = userService.getUserByPasswordResetToken(changePassword.getToken());
        if (user.isPresent()) {
            userService.changeUserPassword(user.get(), changePassword.getNewPassword());
            return ResponseEntity.ok(new MessageResponse("Reset password succesfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Can not find user"));
        }
    }
//        if(userRepository.existsByUsername(loginRequest.getUsername())){
//            if(userService.changePassword(loginRequest.getUsername(), loginRequest.getPassword())) {
//                return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
//            } else {
//                throw new BadRequestException("Unable to change password. Try again!");
//            }
//        } else {
//            throw new BadRequestException("User not found with this email id");
//        }
}

