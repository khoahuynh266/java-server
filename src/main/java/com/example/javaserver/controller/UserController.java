package com.example.javaserver.controller;

import com.example.javaserver.entity.User;
import com.example.javaserver.model.dto.UserDTO;
import com.example.javaserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable(name = "id") int id) {
        // @PathVariable lấy ra thông tin trong URL
        UserDTO result = userService.getUserById(id);
        return ResponseEntity.ok(result);

    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().body(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> UpdateUser(@PathVariable("id") int id, @RequestBody UserDTO user) {
        UserDTO result = userService.updateUser(id, user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteUser(@PathVariable("id") int id)
    {
        try {
          userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user) {
        System.out.println(user);
        UserDTO result = userService.login(user);
        if(result==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       else return ResponseEntity.ok(result);

    }
    }


