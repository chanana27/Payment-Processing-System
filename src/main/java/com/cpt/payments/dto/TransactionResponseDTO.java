package com.cpt.payments.dto;

import lombok.Data;

@Data
public class TransactionResponseDTO {
	
	private String txnReference;
	private String txnStatus;
    private String redirectUrl;
}
