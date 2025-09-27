package com.cpt.payments.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.utils.PaymentMethodConverter;
import com.cpt.payments.utils.PaymentTypeConverter;
import com.cpt.payments.utils.ProviderConverter;
import com.cpt.payments.utils.TransactionStatusConverter;

@Configuration
public class AppConfig {

	@Bean
	ModelMapper getModelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new PropertyMap<TransactionDTO, TransactionEntity>() {
			@Override
			protected void configure() {
				using(new PaymentTypeConverter())
					.map(source.getPaymentType(), destination.getPaymentTypeId());
				using(new PaymentMethodConverter())
					.map(source.getPaymentMethod(), destination.getPaymentMethodId());
				using(new ProviderConverter())
					.map(source.getProvider(), destination.getProviderId());
				using(new TransactionStatusConverter())
					.map(source.getTxnStatus(), destination.getTxnStatusId());
			}
		});
		
		return modelMapper;
	}
}
