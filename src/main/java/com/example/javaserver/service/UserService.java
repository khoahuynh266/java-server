package com.example.javaserver.service;

import com.example.javaserver.entity.User;
import com.example.javaserver.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDTO> getListUser();

    UserDTO getUserById(int id);

    UserDTO addUser(User user);

    UserDTO updateUser(int id, UserDTO user);

     ResponseEntity<?> deleteUser(int id);


    UserDTO login(User user);
}
