package com.yoon.tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yoon.tutorial.jwt.JwtAccessDeniedHandler;
import com.yoon.tutorial.jwt.JwtAuthenticationEntryPoint;
import com.yoon.tutorial.jwt.JwtSecurityConfig;
import com.yoon.tutorial.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize 어노테이션 사용 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		this.tokenProvider = tokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web){
		web
			.ignoring()
			.antMatchers("/h2-console/**", "/favicon.ico");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			//h2-console 설정
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()
			
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 미사용
			
			.and()
			.authorizeHttpRequests()
			.antMatchers("/api/hello").permitAll()
			.antMatchers("/api/authenticate").permitAll()
			.antMatchers("/api/signup").permitAll()
			.anyRequest().authenticated()
			
			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}

}
