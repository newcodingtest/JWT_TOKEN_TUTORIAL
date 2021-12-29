package com.yoon.tutorial.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yoon.tutorial.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
 
	@EntityGraph(attributePaths = "authorities") // Eager��ȸ�� authorities ������ ���� �����´�.
	Optional<User> findOneWithAuthoritiesByUsername(String username);
 }
