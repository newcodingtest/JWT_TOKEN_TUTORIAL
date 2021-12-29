package com.yoon.tutorial.service;



import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoon.tutorial.dto.UserDto;
import com.yoon.tutorial.entity.Authority;
import com.yoon.tutorial.entity.User;
import com.yoon.tutorial.repository.UserRepository;
import com.yoon.tutorial.util.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public User signup(UserDto userDto) {
		if(userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null)!=null) {
			throw  new RuntimeException("이미 가입되어 있는 유저입니다.");
		}
		
		Authority authority = Authority.builder()
				.authorityName("ROLE_USER")
				.build();
		
		User user = User.builder()
				.username(userDto.getUsername())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.nickname(userDto.getNickname())
				.authorities(Collections.singleton(authority))
				.activated(true)
				.build();
				
		return userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public Optional<User> getuserWithAuthorities(String username){
		return userRepository.findOneWithAuthoritiesByUsername(username);
	}
	
	//현재 시큐리티 컨텍스트에 저장된 username의 정보만 가져온다.
	@Transactional(readOnly = true)
	public Optional<User> getMyUserWithAuthorities(){
		return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
	}
	
}
