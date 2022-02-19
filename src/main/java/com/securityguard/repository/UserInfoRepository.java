package com.securityguard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securityguard.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	Optional<UserInfo> findByUsername(String username);
	
	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	boolean existsByUsername(String username);
}
