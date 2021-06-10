package com.example.javaserver.respository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.javaserver.entity.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long idrole);
    Optional<Role> findByRolename(ERole rolename);

}
