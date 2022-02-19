package com.securityguard.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final String message;

	public BusinessException(String message) {
		this.message = message;
	}
}
