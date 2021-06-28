package com.example.javaserver.respository;

import com.example.javaserver.entity.Department;
import com.example.javaserver.entity.ERole;
import com.example.javaserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long idrole);
    Optional<Role> findByRolename(ERole rolename);

    @Override
    List<Role> findAll();
}
