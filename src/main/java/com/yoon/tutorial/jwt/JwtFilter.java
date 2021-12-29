package com.yoon.tutorial.jwt;

import java.io.IOException;
import java.security.Security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean{

	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private TokenProvider tokenProvider;
	
	public JwtFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	//jwt 토큰의 인증정보를 SecurityContext에 저자아는 역할 수행
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String jwt = resolveToken(httpServletRequest);
		String requestURI = httpServletRequest.getRequestURI();
		
		if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(), requestURI);
		}else {
			logger.debug("유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
		}
		
		chain.doFilter(request, response);
		
	}
	
	//Request Header에서 토큰 정보를 꺼내오기 위한 resolveToken 메소드 추가
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	

}
