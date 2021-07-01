package com.example.javaserver.service;

import com.example.javaserver.entity.Department;
import com.example.javaserver.entity.Role;
import com.example.javaserver.entity.User;
import com.example.javaserver.model.dto.UserDTO;
import com.example.javaserver.payload.request.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest, int id);

    boolean changePassword(String username, String password);
     User getByResetPasswordToken(String token) ;
    void createPasswordResetTokenForUser(final User user, final String token);
    String validatePasswordResetToken(String token);
    public Optional<User> getUserByPasswordResetToken(final String token);
    public void changeUserPassword(final User user, final String password);
    ResponseEntity<?> adminUpdatePassword(ChangePasswordRequest changePasswordRequest, int id);
}

