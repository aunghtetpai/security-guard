package com.securityguard.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.securityguard.security.userservice.UserDetailImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private static final String JWT_SIGNIN_KEY = "codigo-exam";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";
	// One day trial Period
	private static final int JWT_EXPIRATION = 86400000;

	public String generateJwtToken(Authentication authentication) {
		UserDetailImpl principal = (UserDetailImpl) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION))
				.signWith(SignatureAlgorithm.HS512, JWT_SIGNIN_KEY).compact();
	}

	public String generateJwtToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION))
				.signWith(SignatureAlgorithm.HS512, JWT_SIGNIN_KEY).compact();
	}

	public String parseJwt(HttpServletRequest request) {
		String str = null;
		String headerAuth = request.getHeader(AUTHORIZATION);
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER)) {
			str = headerAuth.substring(7, headerAuth.length());
		}
		return str;
	}

	public boolean validateJwtToken(String token) throws AuthenticationServiceException {
		try {
			Jwts.parser().setSigningKey(JWT_SIGNIN_KEY).parseClaimsJws(token);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				| IllegalArgumentException e) {
			throw new InsufficientAuthenticationException("invalid jwt");
		}
	}

	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(JWT_SIGNIN_KEY).parseClaimsJws(token).getBody().getSubject();
	}
}
