package com.cpt.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	private int id;
    private int userId;

    private String paymentMethod;
    private String provider;
    private String paymentType;

    private Double amount;
    private String currency;

    private String txnStatus;

    private String merchantTransactionReference;
    private String txnReference;

}
