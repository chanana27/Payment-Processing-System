package com.cpt.payments.constants;

public enum ErrorCodeEnum {
	
	GenericException("20001", "Something went wrong!"),
	Duplicate_Txn_Reference("20002", "Transaction Reference cannot be duplicate!"),
	Payment_Not_Saved("20003", "Couldn't save payment into DB");
	
	private final String errorCode;
	private final String errorMessage;
	
	ErrorCodeEnum(String errorCode, String errorMessage){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	 public String getErrorCode() {
	        return errorCode;
	    }

	 public String getErrorMessage() {
	        return errorMessage;
	    }
}
