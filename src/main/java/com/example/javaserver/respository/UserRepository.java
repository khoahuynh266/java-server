package com.example.javaserver.respository;

import com.example.javaserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);
    User findUserByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    User findUserByUsername(String username);
    @Override
    List<User> findAll();

    public User findByResetPasswordToken(String token);
}

