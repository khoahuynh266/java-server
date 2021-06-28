package com.example.javaserver.service;

import com.example.javaserver.entity.ConfirmationToken;
import com.example.javaserver.entity.Department;
import com.example.javaserver.entity.Role;
import com.example.javaserver.entity.User;
import com.example.javaserver.model.dto.UserDTO;
import com.example.javaserver.payload.request.ChangePasswordRequest;
import com.example.javaserver.payload.request.LoginRequest;
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

    List<Department> getListDepartment();

    List<Role> getListRole();
    ResponseEntity<?> changeStatus(int id, boolean status);
    UserDTO adminUpdateUser(int id, UserDTO user);
  ResponseEntity<?>  changePassword(ChangePasswordRequest changePasswordRequest,int id);
   boolean changePassword(String username , String password);
    void updateResetPasswordToken(String token, String email);
    public ConfirmationToken findByConfirmationToken(String token);
}
