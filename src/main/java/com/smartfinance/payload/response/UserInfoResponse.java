package com.smartfinance.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponse {
	private Long id;
	private String email;
	private String nombre;
}