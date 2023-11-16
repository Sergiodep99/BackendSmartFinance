package com.smartfinance.exception;

public class CustomResourceNotFoundException extends RuntimeException {
	public CustomResourceNotFoundException(String message) {
		super(message);
	}
}