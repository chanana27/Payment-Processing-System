package com.cpt.payments.dto;

import lombok.Data;

@Data
public class InitiateRequestDTO {
	private String firstname;
	private String lastname;
	private String email;
	private String mobilePhone;
	private String brandName;
	private String locale;
	private String country;
	private String returnUrl;
	private String cancelUrl;
}
