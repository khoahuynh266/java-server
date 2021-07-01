package com.example.javaserver.service;

import com.example.javaserver.entity.*;
import com.example.javaserver.model.dto.UserDTO;
import com.example.javaserver.payload.request.ChangePasswordRequest;
import com.example.javaserver.payload.response.MessageResponse;
import com.example.javaserver.respository.DepartmentRepository;
import com.example.javaserver.respository.PasswordResetTokenRepository;
import com.example.javaserver.respository.RoleRepository;
import com.example.javaserver.respository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserServiceIml implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<UserDTO> getListUser() {

        List<UserDTO> result = new ArrayList<UserDTO>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            result.add(modelMapper.map(user, UserDTO.class));
        }
        return result;

    }


    @Override
    public UserDTO getUserById(int id) {
        UserDTO result = null;
        try {
            result = modelMapper.map(userRepository.findUserById(id), UserDTO.class);
        } catch (Exception e) {
        }
        System.out.println(result.getRoles());
        return result;
    }

    @Override
    public UserDTO addUser(User user) {
        UserDTO result = new UserDTO();
        try {
            if (!userRepository.existsByUsername((user.getUsername()))) {
                System.out.println(user);
                if (user.getIdsalary() == null) user.setIdsalary("1");
                user.setActive(true);
                user.setPassword(encoder.encode(user.getPassword()));
                result = modelMapper.map(userRepository.save(user), UserDTO.class);
                // xet role
                Set<Role> roles = user.getRoles();

                System.out.println(roles);
                if (roles == null) {
                    Role userRole = roleRepository.findByRolename(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                } else {
                    roles.forEach(role -> {
                        switch (role.getName()) {
                            case ROLE_ADMIN:
                                Role adminRole = roleRepository.findByRolename(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(adminRole);

                                break;
                            case ROLE_MOD:
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
            } else result = null;
        } catch (Exception e) {
            throw e;
        }
        return result;
    }


    @Override
    public UserDTO updateUser(int id, UserDTO userDto) {
        UserDTO result = null;
        Optional<User> userData = Optional.ofNullable(userRepository.findUserById(id)); // lấy user
        try {
            if (userData.isPresent()) {
                User userUpdate = userData.get();
                System.out.println(userData);

                userUpdate.setId(id);

                if ((userDto.getFullname()) != null)            // update full name
                    userUpdate.setFullname(userDto.getFullname());

                if (userDto.getPhone() != null) userUpdate.setPhone(userDto.getPhone()); // update phone
                if (userDto.getAddress() != null) userUpdate.setAddress(userDto.getAddress());
                if (userDto.getBirthday() != null && userDto.getBirthday() != userUpdate.getBirthday()) {
                    userUpdate.setBirthday(userDto.getBirthday());
                }
                if (userDto.getSex() != null && userDto.getSex() != userUpdate.getSex()) {
                    userUpdate.setSex(userDto.getSex());
                }
                if (userDto.getIddepartment() != null && userDto.getIddepartment() != userUpdate.getIddepartment()) {
                    userUpdate.setIddepartment(userDto.getIddepartment());
                }
                result = modelMapper.map(userRepository.save(userUpdate), UserDTO.class);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public UserDTO adminUpdateUser(int id, UserDTO userDto) {
        UserDTO result = null;
        Optional<User> userData = Optional.ofNullable(userRepository.findUserById(id)); // lấy user
        try {
            if (userData.isPresent()) {
                User userUpdate = userData.get();
                System.out.println(userData);

                userUpdate.setId(id);

                if ((userDto.getFullname()) != null)            // update full name
                    userUpdate.setFullname(userDto.getFullname());

                if (userDto.getPhone() != null) userUpdate.setPhone(userDto.getPhone()); // update phone


                if (userDto.getAddress() != null) userUpdate.setAddress(userDto.getAddress());
                if (userDto.getBirthday() != null && userDto.getBirthday() != userUpdate.getBirthday()) {
                    userUpdate.setBirthday(userDto.getBirthday());
                }
                if (userDto.getSex() != null && userDto.getSex() != userUpdate.getSex()) {
                    userUpdate.setSex(userDto.getSex());
                }
                if (userDto.getIddepartment() != null && userDto.getIddepartment() != userUpdate.getIddepartment()) {
                    userUpdate.setIddepartment(userDto.getIddepartment());
                }

                ERole strRoles = userDto.getRolename();
                System.out.println(strRoles);
                Set<Role> roles = new HashSet<>();
                System.out.println(roles);
                if (strRoles == null) {
                    Role userRole = roleRepository.findByRolename(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                } else {

                    Role userRole = roleRepository.findByRolename(strRoles)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                System.out.println(roles);
                userUpdate.setRoles(roles);
                System.out.println(userUpdate.getRoles());
                result = modelMapper.map(userRepository.save(userUpdate), UserDTO.class);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest, int id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Error: username done exists!"));
        } else {
            User userdb = userRepository.findUserById(id);// Create new user's account
            if (encoder.matches(changePasswordRequest.getOldPassword(), userdb.getPassword())) {
                System.out.println("new pass: " + changePasswordRequest.getNewPassword());
                userdb.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(userdb);
                return ResponseEntity.ok(new MessageResponse("User updated password successfully!"));
            } else
                return ResponseEntity.badRequest().body(new MessageResponse("Old Password not true!"));
        }
    }

    public ResponseEntity<?> changeStatus(int id, boolean status) {

        try {
            Optional<User> userData = Optional.ofNullable(userRepository.findUserById(id)); // lấy user
            if (userData.isPresent()) {
                User userUpdate = userData.get();
                userUpdate.setActive(status);
                userRepository.save(userUpdate);
                return ResponseEntity.ok("Status changed succesfully");
            }
            return ResponseEntity.badRequest().body("change status fail");
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<?> deleteUser(int id) {

        try {
            User user = userRepository.findUserById(id);
            passwordTokenRepository.deleteByUser(user);
            userRepository.deleteById(id);
            return ResponseEntity.ok("User is deleted");
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public List<Department> getListDepartment() {
        List<Department> departments = departmentRepository.findAll();
        return departments;
    }

    @Override
    public List<Role> getListRole() {
        List<Role> roles = roleRepository.findAll();
        return roles;
    }

//    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
//        User user = userRepository.findUserByUsername(email);
//        if (user != null) {
//            user.setResetPasswordToken(token);
//            userRepository.save(user);
//        } else {
//            throw new UsernameNotFoundException("Could not find any user with the email " + email);
//        }
//    }
//
//    public User getByResetPasswordToken(String token) {
//        return userRepository.findByResetPasswordToken(token);
//    }
//
//    public void updatePassword(User user, String newPassword) {
//
//            System.out.println("new pass: " + newPassword);
//            user.setPassword(encoder.encode(newPassword));
//            userRepository.save(user);
//
//        user.setResetPasswordToken(null);
//        userRepository.save(user);
//    }


    public boolean changePassword(String email, String password) {
        User user = userRepository.findUserByUsername(email);
        user.setPassword(encoder.encode(password));
        if (userRepository.save(user) != null) {
            return true;
        }
        return false;
    }

    @Override
    public User getByResetPasswordToken(String token) {

        return userRepository.findByResetPasswordToken(token);
    }


    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> adminUpdatePassword(ChangePasswordRequest changePasswordRequest, int id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Error: username done exists!"));
        } else {
            User userdb = userRepository.findUserById(id);// Create new user's account
            System.out.println("new pass: " + changePasswordRequest.getNewPassword());
            userdb.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(userdb);
            return ResponseEntity.ok(new MessageResponse("User updated password successfully!"));
        }
    }
}