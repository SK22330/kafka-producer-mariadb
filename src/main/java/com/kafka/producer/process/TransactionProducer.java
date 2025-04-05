package com.kafka.producer.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.kafka.producer.model.TransactionMigrationModel;
import com.kafka.producer.model.TransactionModel;
import com.kafka.producer.service.transaction.TransactionService;
import com.kafka.producer.service.transactionmigration.TransactionMigrationService;

@Component
public class TransactionProducer {

	@Autowired
	private TransactionMigrationService migrationService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private KafkaTemplate<String, TransactionModel> kafkaTemplate;

	@EventListener(ApplicationReadyEvent.class) // Runs after app starts
	public void migrateData() {
		int batchSize = 5;

		while (true) { // Infinite loop to run continuously
			List<TransactionModel> batch = transactionService.getUnmigratedRecords(batchSize);
			List<TransactionMigrationModel> list = new ArrayList<>();

			if (!batch.isEmpty()) {
				for (TransactionModel record : batch) {

					kafkaTemplate.send("transaction-topic", record).thenAccept(result -> {
						System.out.println("Message sent successfully: " + result.getRecordMetadata());
						TransactionMigrationModel model = new TransactionMigrationModel();
						model.setTransactionId(record.getId());
						model.setStatus(1);
						list.add(model);
					}).exceptionally(ex -> {
						System.err.println("Failed to send message: " + ex.getMessage());
						TransactionMigrationModel model = new TransactionMigrationModel();
						model.setTransactionId(record.getId());
						model.setStatus(2);
						list.add(model);
						return null;
					});
				}
			} else {
				System.out.println("No new records to migrate. Waiting...");
			}

			if (!list.isEmpty()) {
				migrationService.saveAll(list);
			}

			try {
				Thread.sleep(1000); // Wait for 5 seconds before checking again
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Migration process interrupted", e);
			}
		}
	}
}
