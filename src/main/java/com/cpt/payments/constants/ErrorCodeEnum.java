package com.cpt.payments.constants;

public enum ErrorCodeEnum {
	
	GenericException("20001", "Something went wrong!"),
	Duplicate_Txn_Reference("20002", "Transaction Reference cannot be duplicate!"),
	Payment_Not_Saved("20003", "Couldn't save payment into DB"),
    Initiate_Transaction_Failed("20004", "Could not find transaction to initiate with given txnReference"),
	Transaction_Already_Initiated("20005", "Transaction is already initiated"),
    Cannot_Initiate("20006", "Could not initiate transaction");

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
