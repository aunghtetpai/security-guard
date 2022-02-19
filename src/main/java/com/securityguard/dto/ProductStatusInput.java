package com.securityguard.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductStatusInput implements Serializable {

	private static final long serialVersionUID = 6427420682582185709L;

	private long adminId;
	private long productId;
	private String product;
	private String status;
}
