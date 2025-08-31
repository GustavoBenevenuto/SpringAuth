package com.springauth.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.springauth.domain.users.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	UserDetails findByEmail(String email);
}
