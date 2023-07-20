package com.ivchern.exchange_employers.DTO.Payload.Request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	@Schema( example = "test2")
  	private String username;

	@NotBlank
	@Schema( example = "123456")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
