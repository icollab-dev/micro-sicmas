package com.mx.microsicmas.multitenant.exception;

public class TenantNotFoundException extends Exception {
	public TenantNotFoundException(String message) {
		super(message);
	}
}
