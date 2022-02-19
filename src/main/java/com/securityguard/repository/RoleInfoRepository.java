package com.securityguard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securityguard.common.Role;
import com.securityguard.entity.RoleInfo;

@Repository
public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {

	Optional<RoleInfo> findByRole(Role role);
}
