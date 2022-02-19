package com.securityguard.security.userservice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.securityguard.entity.UserInfo;

import lombok.Data;

@Data
public class UserDetailImpl implements UserDetails {

	private static final long serialVersionUID = 1249970545072652573L;

	private long id;
	private String username;
	@JsonIgnore
	private String password;
	private String email;
	private String phone;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailImpl(long id, String username, String password, String email, String phone,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.authorities = authorities;
	}

	public static UserDetailImpl build(UserInfo user) {
		List<GrantedAuthority> authorities = Stream.of(new SimpleGrantedAuthority(user.getRoleInfo().getRole().name()))
				.collect(Collectors.toList());
		return new UserDetailImpl(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(),
				user.getPhone(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
