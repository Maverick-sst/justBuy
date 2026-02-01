package com.example.Backend.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Backend.entity.UserModel;

public interface UserRepository extends JpaRepository<UserModel,UUID> {
    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);
}
