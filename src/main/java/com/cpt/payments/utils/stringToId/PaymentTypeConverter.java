package com.cpt.payments.utils.stringToId;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.cpt.payments.constants.PaymentTypeEnum;

public class PaymentTypeConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(MappingContext<String, Integer> context) {
		if(context.getSource() == null)
				return null;
		
		return PaymentTypeEnum.getEnumByName(context.getSource()).getId();
	}

}
