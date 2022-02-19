package com.securityguard.common;

import com.securityguard.exception.BusinessException;

public enum Role {
	ROLE_ADMIN, ROLE_USER;

	public static Role of(String val) {
		Role _role = null;
		for (Role role : Role.values()) {
			if (role.name().equals(val)) {
				_role = role;
			}
		}
		if (_role == null) {
			throw new BusinessException("No Role found. Please contact Adminstrator.");
		}
		return _role;
	}
}
