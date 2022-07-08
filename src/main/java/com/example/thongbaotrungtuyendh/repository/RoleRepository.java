package com.example.thongbaotrungtuyendh.repository;

import com.example.thongbaotrungtuyendh.entity.account.ERole;
import com.example.thongbaotrungtuyendh.entity.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
