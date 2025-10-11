package com.cpt.payments.utils.idToString;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.cpt.payments.constants.PaymentTypeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;

public class TransactionStatusIdConverter implements Converter<Integer, String> {

	@Override
	public String convert(MappingContext<Integer, String> context) {
		if(context.getSource() == null)
				return null;
		
		return TransactionStatusEnum.getEnumById(context.getSource()).getName();
	}

}
