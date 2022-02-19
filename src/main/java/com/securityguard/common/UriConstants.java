package com.securityguard.common;

public class UriConstants {

	// Authentication URL
	public static final String AUTH_URL = "/api/auth/**";

	private static final String CMS_BASE_URL = "http://localhost:8081/api/cms/evoucher";
	private static final String ESTORE_BASE_URL = "http://localhost:8082/api/cms/estore";

	// E-voucher Management URL
	public static final String CMS_VOUCHER_CREATE = CMS_BASE_URL + "/create";
	public static final String CMS_VOUCHER_UPDATE = CMS_BASE_URL + "/update/";
	public static final String CMS_PRODUCT_STATUS_UPDATE = CMS_BASE_URL + "/product/status";
	public static final String CMS_VOUCHERS_CREATE = CMS_BASE_URL + "/vouchers";

	// E-store Management URL
	public static final String CMS_ESTORE_VOUCHERS = ESTORE_BASE_URL + "/vouchers/";
	public static final String CMS_ESTORE_VOUCHERDETAILS = ESTORE_BASE_URL + "/voucherdetails/";
	public static final String CMS_ESTORE_PAYMENTS = ESTORE_BASE_URL + "/payments/";
	public static final String CMS_ESTORE_PAYMENT_VOUCHER = ESTORE_BASE_URL + "/payment/";
	public static final String CMS_ESTORE_PROMOCODE_CHECK = ESTORE_BASE_URL + "/promocode/";

	// AUTH
	public static final String SIGN_UP = "/api/auth/signUp";
	public static final String SIGN_IN = "/api/auth/signIn";
	public static final String ESTORE = "estore";
}
