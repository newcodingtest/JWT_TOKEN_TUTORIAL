package com.yoon.tutorial.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoon.tutorial.dto.UserDto;
import com.yoon.tutorial.entity.User;
import com.yoon.tutorial.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto){
		return ResponseEntity.ok(userService.signup(userDto));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<User> getMyUserInfo(){
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}
	
	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<User> getUserInfo(@PathVariable String username){
		return ResponseEntity.ok(userService.getuserWithAuthorities(username).get());
	}
	
	
	
}
