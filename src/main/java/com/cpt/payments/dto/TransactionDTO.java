package com.cpt.payments.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
	
	private int id;
    private int userId;

    private int paymentMethodId;
    private int providerId;
    private int paymentTypeId;

    private Double amount;
    private String currency;

    private int txnStatusId;

    private String merchantTransactionReference;
    private String txnReference;
    
    private String providerCode;
    private String providerMessage;
    private String providerReference;

    private Timestamp creationDate;
    private Timestamp updatedDate;

    private int retryCount;

}
