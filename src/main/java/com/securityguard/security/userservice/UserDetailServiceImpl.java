package com.securityguard.security.userservice;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securityguard.entity.UserInfo;
import com.securityguard.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserInfoRepository userInfoReposiory;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userInfoReposiory.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username(" + username + ") is not found"));
		return UserDetailImpl.build(user);
	}

}
