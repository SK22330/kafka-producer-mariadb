package com.kafka.producer.service.transactionmigration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.producer.model.TransactionMigrationModel;
import com.kafka.producer.repository.TransactionMigrationRepo;

@Service
public class TransactionMigrationServiceImpl implements TransactionMigrationService {

	@Autowired
	private TransactionMigrationRepo repo;

	@Override
	public List<TransactionMigrationModel> saveAll(List<TransactionMigrationModel> list) {
		return repo.saveAll(list);
	}

}
