package com.kafka.producer.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	private TransactionProducer transactionProducer;

	@Override
	public void run(String... args) throws Exception {
		transactionProducer.migrateData();
	}

}
