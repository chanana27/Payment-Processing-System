package com.cpt.payments.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
	
	private String txnReference;
	private String txnStatus;	
}
