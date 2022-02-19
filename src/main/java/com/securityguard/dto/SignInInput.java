package com.securityguard.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignInInput implements Serializable {

	private static final long serialVersionUID = 386192462198757506L;

	private String username;
	private String password;
}
