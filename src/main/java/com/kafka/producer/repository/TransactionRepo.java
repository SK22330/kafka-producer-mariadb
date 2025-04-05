package com.kafka.producer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kafka.producer.model.TransactionModel;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionModel, Integer> {

	@Query(value = """
			SELECT t.*
			FROM transaction t
			LEFT JOIN transaction_migration tm ON t.id = tm.transaction_id
			WHERE tm.transaction_id IS NULL OR tm.status != 1
			ORDER BY tm.id ASC
			LIMIT :limit
			""", nativeQuery = true)
	List<TransactionModel> getAll(int limit);

}
