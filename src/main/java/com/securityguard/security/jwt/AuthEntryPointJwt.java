package com.securityguard.security.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.securityguard.dto.ErrorMessageDto;
import com.securityguard.exception.BusinessException;
import com.securityguard.json.JsonObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	private final JsonObjectMapper mapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = response.getWriter();
		ErrorMessageDto dto = null;
		if (authException instanceof InsufficientAuthenticationException) {
			var message = "Your authentication is expired or invalid. Please login again.";
			dto = new ErrorMessageDto(HttpServletResponse.SC_UNAUTHORIZED, message);
		} else if (authException instanceof AuthenticationCredentialsNotFoundException) {
			var message = authException.getMessage();
			dto = new ErrorMessageDto(HttpServletResponse.SC_UNAUTHORIZED, message);
		} else {
			var message = "Username or password incorrect.";
			dto = new ErrorMessageDto(HttpServletResponse.SC_UNAUTHORIZED, message);
		}
		String responseMessage = null;
		try {
			responseMessage = mapper.serialize(dto);
		} catch (BusinessException e) {
			responseMessage = e.getMessage();
		}
		writer.print(responseMessage);
		writer.flush();
		writer.close();
	}
}
