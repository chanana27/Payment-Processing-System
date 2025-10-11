package com.cpt.payments.utils.idToString;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.cpt.payments.constants.PaymentTypeEnum;

public class PaymentTypeIdConverter implements Converter<Integer, String> {

	@Override
	public String convert(MappingContext<Integer, String> context) {
		if(context.getSource() == null)
				return null;
		
		return PaymentTypeEnum.getEnumById(context.getSource()).getName();
	}

}
