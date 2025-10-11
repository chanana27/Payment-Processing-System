package com.cpt.payments.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.utils.idToString.PaymentMethodIdConverter;
import com.cpt.payments.utils.idToString.PaymentTypeIdConverter;
import com.cpt.payments.utils.idToString.ProviderIdConverter;
import com.cpt.payments.utils.idToString.TransactionStatusIdConverter;
import com.cpt.payments.utils.stringToId.PaymentMethodConverter;
import com.cpt.payments.utils.stringToId.PaymentTypeConverter;
import com.cpt.payments.utils.stringToId.ProviderConverter;
import com.cpt.payments.utils.stringToId.TransactionStatusConverter;

@Configuration
public class AppConfig {

	@Bean
	ModelMapper getModelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.getConfiguration().setMatchingStrategy(
                MatchingStrategies.STRICT);
		
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
		
		modelMapper.addMappings(new PropertyMap<TransactionEntity, TransactionDTO>() {
			@Override
			protected void configure() {
				using(new PaymentTypeIdConverter())
					.map(source.getPaymentTypeId(), destination.getPaymentType());
				
				using(new PaymentMethodIdConverter())
					.map(source.getPaymentMethodId(), destination.getPaymentMethod());
				
				using(new ProviderIdConverter())
					.map(source.getProviderId(), destination.getProvider());
				
				using(new TransactionStatusIdConverter())
					.map(source.getTxnStatusId(), destination.getTxnStatus());
			}
		});

		
		return modelMapper;
	}
}
