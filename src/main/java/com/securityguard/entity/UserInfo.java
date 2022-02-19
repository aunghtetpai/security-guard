package com.securityguard.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1572206359708046037L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "username", nullable = false, updatable = false, length = 100)
	private String username;
	@Column(name = "password", length = 255, updatable = true, nullable = false)
	private String password;
	@Column(name = "phone", nullable = false, updatable = true, length = 50)
	private String phone;
	@Column(name = "email", nullable = false, updatable = true, length = 100)
	private String email;
	@OneToOne
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private RoleInfo roleInfo;
}
