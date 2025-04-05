package com.kafka.producer.service.transaction;

import java.util.List;

import com.kafka.producer.model.TransactionModel;

public interface TransactionService {
	
	List<TransactionModel> getUnmigratedRecords(int limit);

}
