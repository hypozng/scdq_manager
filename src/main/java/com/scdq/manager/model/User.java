package com.scdq.manager.model;

/**
 * 用户实体类
 */
public class User extends BasicModel {
	// 用户名
	private String username;

	// 电话号码
	private String phone;

	// 邮箱
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
