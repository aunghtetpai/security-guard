package com.securityguard.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInOutput implements Serializable {

	private static final long serialVersionUID = -5728604834403653575L;

	private long id;
	private String username;
	private String jwtToken;
	private String role;
}
