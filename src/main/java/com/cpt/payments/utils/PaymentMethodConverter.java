package com.cpt.payments.utils;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.cpt.payments.constants.PaymentMethodEnum;
import com.cpt.payments.constants.PaymentTypeEnum;

public class PaymentMethodConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(MappingContext<String, Integer> context) {
		if(context.getSource() == null)
				return null;
		
		return PaymentMethodEnum.getEnumByName(context.getSource()).getId();
	}

}
