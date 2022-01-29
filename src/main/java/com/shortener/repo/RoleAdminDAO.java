package com.shortener.repo;

import com.shortener.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleAdminDAO extends JpaRepository<RoleUser, UUID> {
    Optional<RoleUser> findByRole (String role);
}
