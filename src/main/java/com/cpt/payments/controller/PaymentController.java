package com.cpt.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constants.EndPoints;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.Transaction;
import com.cpt.payments.service.interfaces.PaymentStatusService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(EndPoints.V1_PAYMENTS)
@Slf4j
public class PaymentController {
	
	private ModelMapper modelMapper;
	private PaymentStatusService paymentStatusService;
	
	public PaymentController(ModelMapper modelMapper, PaymentStatusService paymentStatusService) {
		this.modelMapper = modelMapper;
		this.paymentStatusService = paymentStatusService;
	}

	@PostMapping("/create")
		public ResponseEntity<PaymentResponse> createPayment(@RequestBody Transaction transaction) {
			log.info("In controller received transaction object as {}", transaction);
			
			TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
			log.info("Converted Transaction to TransactionDTO as {}", transactionDTO);
			
			PaymentResponseDTO responseDTO = paymentStatusService.insertPayment(transactionDTO);
			log.info("Received responseDTO from service as {}", responseDTO);
			PaymentResponse response = modelMapper.map(responseDTO, PaymentResponse.class);
			
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}	
}
