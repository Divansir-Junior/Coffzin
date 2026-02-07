package com.coffzin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coffzin.model.User;
public interface  UserRepository extends JpaRepository<User, Long> {

}
