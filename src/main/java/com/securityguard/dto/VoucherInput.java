package com.securityguard.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class VoucherInput implements Serializable {

	private static final long serialVersionUID = 5590970565807229246L;

	private long productId;
	private String product;
	private String title;
	private String description;
	private Double amount;
	private String paymentMethod;
	private Integer quantity;
	private String buyType;
	private long adminId;
	private String phone;
}
