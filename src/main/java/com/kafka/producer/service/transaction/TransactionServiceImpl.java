package com.kafka.producer.service.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.producer.model.TransactionModel;
import com.kafka.producer.repository.TransactionRepo;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepo repo;
	
	public List<TransactionModel> getUnmigratedRecords(int limit) {
		return repo.getAll(limit);
	}

}
