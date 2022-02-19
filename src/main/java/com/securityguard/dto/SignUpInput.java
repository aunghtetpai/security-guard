package com.securityguard.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignUpInput implements Serializable {

	private static final long serialVersionUID = 504240873697865683L;

	private String username;
	private String password;
	private String phone;
	private String email;
	private String role;
}
