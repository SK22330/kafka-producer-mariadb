package com.kafka.producer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kafka.producer.model.TransactionMigrationModel;

@Repository
public interface TransactionMigrationRepo extends JpaRepository<TransactionMigrationModel, Integer> {
}
