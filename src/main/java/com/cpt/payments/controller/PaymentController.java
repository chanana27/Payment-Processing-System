package com.cpt.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constants.EndPoints;
import com.cpt.payments.dto.InitiateTxnRequestDTO;
import com.cpt.payments.dto.TransactionResponseDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.pojo.InitiateRequest;
import com.cpt.payments.pojo.TransactionRes;
import com.cpt.payments.pojo.Transaction;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.PaymentStatusService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(EndPoints.V1_PAYMENTS)
@Slf4j
public class PaymentController {
	
	private ModelMapper modelMapper;
	private PaymentStatusService paymentStatusService;
	private PaymentService paymentService;
	
	public PaymentController(ModelMapper modelMapper, PaymentStatusService paymentStatusService,
			PaymentService paymentService) {
		this.modelMapper = modelMapper;
		this.paymentStatusService = paymentStatusService;
		this.paymentService = paymentService;
	}

	@PostMapping("/create")
		public ResponseEntity<TransactionRes> createPayment(@RequestBody Transaction transaction) {
			log.info("In controller received transaction as {}", transaction);
			
			TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
			log.info("Converted Transaction to TransactionDTO as {}", transactionDTO);
			
			TransactionResponseDTO responseDTO = paymentStatusService.insertPayment(transactionDTO);
			log.info("Received responseDTO from service as {}", responseDTO);
			TransactionRes response = modelMapper.map(responseDTO, TransactionRes.class);

            log.info("Payment created successfully {}", response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}

	@PostMapping(EndPoints.INITIATE )
	public ResponseEntity<TransactionRes> initiatePayment(@PathVariable String txnReference,
                                                          @RequestBody InitiateRequest initiateRequest) {
		
		log.info("Initiating Payment for txnReference:{}|initiateRequest:{}", txnReference, initiateRequest);
		
		InitiateTxnRequestDTO initiateTxnRequestDTO = modelMapper.map(initiateRequest, InitiateTxnRequestDTO.class);

		TransactionResponseDTO responseDTO = paymentService.initiatePayment(initiateTxnRequestDTO, txnReference);
		TransactionRes response = modelMapper.map(responseDTO, TransactionRes.class);
		
		log.info("Payment Initiated successfully {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}
}
