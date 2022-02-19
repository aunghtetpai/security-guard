package com.securityguard.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securityguard.common.JwtUtils;
import com.securityguard.common.Role;
import com.securityguard.dto.SignInInput;
import com.securityguard.dto.SignInOutput;
import com.securityguard.dto.SignUpInput;
import com.securityguard.entity.UserInfo;
import com.securityguard.exception.BusinessException;
import com.securityguard.repository.RoleInfoRepository;
import com.securityguard.repository.UserInfoRepository;
import com.securityguard.security.userservice.UserDetailImpl;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserInfoRepository userInfoRepository;
	private final RoleInfoRepository roleInfoRepository;
	private final JwtUtils jwtUtils;

	public String signUp(SignUpInput input) throws BusinessException {
		try {
			this.check(input);
			if (userInfoRepository.existsByUsername(input.getUsername())) {
				throw new BusinessException("Username(" + input.getUsername() + ") is already registered.");
			}
			if (userInfoRepository.existsByEmail(input.getEmail())) {
				throw new BusinessException("Email(" + input.getEmail() + ") is already registered.");
			}
			if (userInfoRepository.existsByPhone(input.getPhone())) {
				throw new BusinessException("Phone(" + input.getPhone() + ") is already registered.");
			}
			Role role = Role.of(input.getRole().toUpperCase());
			var roleInfo = roleInfoRepository.findByRole(role).orElseThrow(
					() -> new BusinessException("No Role informaion is not found. Please contact Administrator."));
			UserInfo user = new UserInfo();
			user.setUsername(input.getUsername());
			user.setPassword(passwordEncoder.encode(input.getPassword()));
			user.setPhone(input.getPhone());
			user.setEmail(input.getEmail());
			user.setRoleInfo(roleInfo);
			userInfoRepository.save(user);
			return "User is successfully created. Please login in.";
		} catch (BusinessException e) {
			throw e;
		}
	}

	public SignInOutput signIn(SignInInput input) throws BusinessException {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = jwtUtils.generateJwtToken(authentication);
			UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			return new SignInOutput(userDetails.getId(), userDetails.getUsername(), jwtToken, roles.get(0));
		} catch (BusinessException e) {
			throw e;
		}
	}

	private void check(SignUpInput input) throws BusinessException {
		if (input.getUsername() == null || input.getUsername().isBlank()) {
			throw new BusinessException("Username must not be null.");
		}
		if (input.getPassword() == null || input.getPassword().isBlank()) {
			throw new BusinessException("Password must not be null.");
		}
		if (input.getPhone() == null || input.getPhone().isBlank()) {
			throw new BusinessException("Phone must not be null.");
		}
		if (input.getEmail() == null || input.getEmail().isBlank()) {
			throw new BusinessException("Email must not be null.");
		}
		if (input.getRole() == null) {
			throw new BusinessException("Role must not be null.");
		}
	}
}
