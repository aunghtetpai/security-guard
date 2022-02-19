package com.securityguard.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.securityguard.common.JwtUtils;
import com.securityguard.common.UriConstants;
import com.securityguard.security.userservice.UserDetailServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String uri = request.getRequestURI();
			String jwt = jwtUtils.parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUsernameFromJwtToken(jwt);
				UserDetails details = userDetailService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details,
						null, details.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else if (!uri.equals(UriConstants.SIGN_IN) && !uri.equals(UriConstants.SIGN_UP)
					&& !uri.contains(UriConstants.ESTORE)) {
				throw new AuthenticationCredentialsNotFoundException("Please add Authorization(JWT Token) to Header.");
			}
		} catch (Exception e) {
			throw e;
		}
		filterChain.doFilter(request, response);
	}
}
