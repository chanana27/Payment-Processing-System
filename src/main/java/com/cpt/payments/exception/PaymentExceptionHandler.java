package com.cpt.payments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.pojo.PaymentError;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class PaymentExceptionHandler {

	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<PaymentError> handleCustomeUncheckedException(PaymentException ex) {
        
	 	log.info("Returning from PaymentExceptionHandler lopo {}", ex);
	 	PaymentError paymentError = new PaymentError(ex.getErrorCode(), ex.getErrorMessage());
	 	
	 	return new ResponseEntity<>(paymentError, ex.getHttpStatus());
	 
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<PaymentError> handleGenericError() {
        
	 	log.info("Returning from PaymentExceptionHandler generic exception");
	 	PaymentError paymentError = new PaymentError(
	 			ErrorCodeEnum.GenericException.getErrorCode()
	 			, ErrorCodeEnum.GenericException.getErrorMessage());
	 	
	 	return new ResponseEntity<>(paymentError, HttpStatus.INTERNAL_SERVER_ERROR);
	 
    }
	
}
