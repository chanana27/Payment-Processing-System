package com.cpt.payments.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {
	
	
	private static final long serialVersionUID = -998347914869283755L;
	private final String errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatus;
	
	public PaymentException(String errorCode, String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}
}
