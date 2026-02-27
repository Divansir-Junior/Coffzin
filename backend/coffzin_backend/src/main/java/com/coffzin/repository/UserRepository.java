package com.coffzin.repository;

import com.coffzin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}