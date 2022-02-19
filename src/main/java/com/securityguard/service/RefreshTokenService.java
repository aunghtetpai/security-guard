package com.securityguard.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securityguard.common.JwtUtils;
import com.securityguard.exception.BusinessException;
import com.securityguard.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

	private final UserInfoRepository userInfoRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public String refreshToken(long id) {
		var user = userInfoRepository.findById(id)
				.orElseThrow(() -> new BusinessException("User Information is not found."));
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtUtils.generateJwtToken(authentication);
		return jwtToken;
	}
}
