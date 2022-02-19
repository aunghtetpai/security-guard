package com.securityguard.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.securityguard.common.UriConstants;
import com.securityguard.dto.ProductStatusInput;
import com.securityguard.dto.SignInInput;
import com.securityguard.dto.SignUpInput;
import com.securityguard.dto.VoucherInput;
import com.securityguard.dto.VoucherPaymentInput;
import com.securityguard.exception.BusinessException;
import com.securityguard.json.JsonObjectMapper;
import com.securityguard.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class SecurityGuardController {

	private final AccountService accountService;
	private final JsonObjectMapper mapper;

	@PostMapping(value = "/signIn", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> signIn(@RequestBody SignInInput input) {
		try {
			var response = accountService.signIn(input);
			return ResponseEntity.ok(mapper.serialize(response));
		} catch (BusinessException e) {
			throw e;
		}
	}

	@PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> signUp(@RequestBody SignUpInput input) {
		try {
			var response = accountService.signUp(input);
			return ResponseEntity.ok(mapper.serialize(response));
		} catch (BusinessException e) {
			throw e;
		}
	}

	@PostMapping(value = "/cms/evoucher/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> evoucherCreate(@RequestBody VoucherInput input) {
		try {
			if (input.getProduct() == null || input.getAmount() == null || input.getPaymentMethod() == null
					|| input.getQuantity() == null || input.getBuyType() == null || input.getPhone() == null) {
				throw new BusinessException("Some values are missing in request body. Plz input all values.");
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<VoucherInput> entity = new HttpEntity<>(input, headers);
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.postForEntity(UriConstants.CMS_VOUCHER_CREATE, entity, String.class);
		} catch (BusinessException e) {
			throw e;
		}
	}

	@PostMapping(value = "/cms/evoucher/update/{voucherNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> evoucherUpdate(@RequestBody VoucherInput input,
			@PathVariable("voucherNo") String voucherNo) {
		try {
			if (input.getAmount() == null || input.getPaymentMethod() == null | input.getBuyType() == null) {
				throw new BusinessException("Some values are missing in request body. Plz input all values.");
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<VoucherInput> entity = new HttpEntity<>(input, headers);
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.postForEntity(UriConstants.CMS_VOUCHER_UPDATE.concat(voucherNo), entity, String.class);
		} catch (BusinessException e) {
			throw e;
		}
	}

	@PostMapping(value = "/cms/evoucher/product/status", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateStatus(@RequestBody ProductStatusInput input) {
		try {
			if (input.getProduct() == null || input.getStatus() == null) {
				throw new BusinessException("Some values are missing in request body. Plz input all values.");
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<ProductStatusInput> entity = new HttpEntity<>(input, headers);
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.postForEntity(UriConstants.CMS_PRODUCT_STATUS_UPDATE, entity, String.class);
		} catch (BusinessException e) {
			throw e;
		}
	}

	@GetMapping(value = "/cms/estore/vouchers/{phone}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getVouchers(@PathVariable("phone") String phone) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = UriConstants.CMS_ESTORE_VOUCHERS.concat(phone);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object());
		} catch (BusinessException e) {
			throw e;
		}
	}

	@GetMapping(value = "/cms/estore/voucherdetails/{phone}/{voucherNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getVoucherDetails(@PathVariable("phone") String phone,
			@PathVariable("voucherNo") String voucherNo) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = UriConstants.CMS_ESTORE_VOUCHERDETAILS.concat(phone).concat("/").concat(voucherNo);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object());
		} catch (BusinessException e) {
			throw e;
		}
	}

	@GetMapping(value = "/cms/estore/payments/{productId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPayments(@PathVariable("productId") String productId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = UriConstants.CMS_ESTORE_PAYMENTS.concat(productId);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object());
		} catch (BusinessException e) {
			throw e;
		}
	}

	@PostMapping(value = "/cms/estore/payment/{phone}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setVoucherPayment(@RequestBody VoucherPaymentInput input,
			@PathVariable("phone") String phone) {
		try {
			if (input.getVoucherNo() == null || input.getCard() == null || input.getCardNo() == null
					|| input.getName() == null || input.getSecurityCode() == null) {
				throw new BusinessException("Some values are missing in request body. Plz input all values.");
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<VoucherPaymentInput> entity = new HttpEntity<>(input, headers);
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.postForEntity(UriConstants.CMS_ESTORE_PAYMENT_VOUCHER.concat(phone), entity,
					String.class);
		} catch (BusinessException e) {
			throw e;
		}
	}

	@GetMapping(value = "/cms/estore/promocode/{phone}/{promocode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkPromocode(@PathVariable("phone") String phone,
			@PathVariable("promocode") String promocode) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = UriConstants.CMS_ESTORE_PROMOCODE_CHECK.concat(phone).concat("/").concat(promocode);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new Object());
		} catch (BusinessException e) {
			throw e;
		}
	}

	@PostMapping(value = "/cms/evoucher/vouchers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> evouchersCreate(@RequestBody VoucherInput input) {
		try {
			if (input.getProduct() == null || input.getAmount() == null || input.getPaymentMethod() == null
					|| input.getBuyType() == null || input.getPhone() == null) {
				throw new BusinessException("Some values are missing in request body. Plz input all values.");
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<VoucherInput> entity = new HttpEntity<>(input, headers);
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.postForEntity(UriConstants.CMS_VOUCHERS_CREATE, entity, String.class);
		} catch (BusinessException e) {
			throw e;
		}
	}
}
