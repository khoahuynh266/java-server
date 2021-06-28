package com.example.javaserver.controller;

import com.example.javaserver.entity.Department;
import com.example.javaserver.entity.Role;
import com.example.javaserver.entity.User;
import com.example.javaserver.model.dto.UserDTO;
import com.example.javaserver.payload.request.ChangePasswordRequest;
import com.example.javaserver.payload.response.MessageResponse;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaserver.service.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/account")
public class UserController {
    @Autowired
    public UserService userService;

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getListAccount() {
        List<UserDTO> users = userService.getListUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/deparments")
    public ResponseEntity<List<Department>> getListDeparment() {
        List<Department> deparment = userService.getListDepartment();
        return ResponseEntity.ok(deparment);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getListRole() {
        List<Role> role = userService.getListRole();
        return ResponseEntity.ok(role);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable(name = "id") int id) {
        // @PathVariable lấy ra thông tin trong URL
        UserDTO result = userService.getUserById(id);
        return ResponseEntity.ok(result);

    }
    @PostMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@RequestBody int id) {
        // @PathVariable lấy ra thông tin trong URL

        UserDTO result = userService.getUserById(id);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.addUser(user) == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already exist"));
        } else return ResponseEntity.ok().body(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> UpdateUser(@PathVariable("id") int id, @RequestBody UserDTO user) {
        UserDTO result = userService.updateUser(id, user);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/admin/{id}")
    public ResponseEntity<UserDTO> adminUpdateUser(@PathVariable("id") int id, @RequestBody UserDTO user) {
        UserDTO result = userService.adminUpdateUser(id, user);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<UserDTO> changeStatus(@PathVariable("id") int id, @RequestBody boolean status) {
        try {
            userService.changeStatus(id, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/changePassword/{id}")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                            @PathVariable("id") int id) {
        return userService.changePassword(changePasswordRequest,id);
    }



}


