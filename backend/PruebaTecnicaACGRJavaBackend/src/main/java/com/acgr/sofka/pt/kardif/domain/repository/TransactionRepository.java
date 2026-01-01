package com.acgr.sofka.pt.kardif.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;

public interface TransactionRepository extends ReactiveCrudRepository<TransactionRecord, Long> {
}
