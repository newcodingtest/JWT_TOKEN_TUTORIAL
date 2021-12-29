package com.yoon.tutorial.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yoon.tutorial.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
 
	@EntityGraph(attributePaths = "authorities") // Eager조회로 authorities 정보를 같이 가져온다.
	Optional<User> findOneWithAuthoritiesByUsername(String username);
 }
