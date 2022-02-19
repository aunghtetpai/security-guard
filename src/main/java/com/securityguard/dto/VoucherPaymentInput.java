package com.securityguard.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class VoucherPaymentInput implements Serializable {

	private static final long serialVersionUID = 2011717214067635536L;

	private String voucherNo;
	private String card;
	private String cardNo;
	private String securityCode;
	private String name;
}
