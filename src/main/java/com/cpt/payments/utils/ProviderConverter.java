package com.cpt.payments.utils;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.cpt.payments.constants.PaymentTypeEnum;
import com.cpt.payments.constants.ProviderEnum;

public class ProviderConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(MappingContext<String, Integer> context) {
		if(context.getSource() == null)
				return null;
		
		return ProviderEnum.getEnumByName(context.getSource()).getId();
	}

}
