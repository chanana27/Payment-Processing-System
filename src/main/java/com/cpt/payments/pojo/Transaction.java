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

    private int paymentMethodId;
    private int providerId;
    private int paymentTypeId;

    private Double amount;
    private String currency;

    private int txnStatusId;

    private String merchantTransactionReference;
    private String txnReference;

}
