package com.kafka.producer.service.transactionmigration;

import java.util.List;

import com.kafka.producer.model.TransactionMigrationModel;

public interface TransactionMigrationService {

	List<TransactionMigrationModel> saveAll(List<TransactionMigrationModel> list);

}
