package com.securityguard.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDto implements Serializable {

	private static final long serialVersionUID = 2984585556019033494L;

	private Integer status;
	private String message;
}
