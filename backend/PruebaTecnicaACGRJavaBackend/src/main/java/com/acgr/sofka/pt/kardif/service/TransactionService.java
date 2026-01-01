package com.acgr.sofka.pt.kardif.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;
import com.acgr.sofka.pt.kardif.domain.repository.TransactionRepository;

import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private static final BigDecimal THRESHOLD = new BigDecimal("10000");
    private static final BigDecimal HIGH_RATE = new BigDecimal("0.05");
    private static final BigDecimal LOW_RATE = new BigDecimal("0.02");

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<TransactionRecord> registerTransaction(BigDecimal amount) {
        BigDecimal commission = calculateCommission(amount);
        TransactionRecord record = new TransactionRecord(null, amount, commission, LocalDateTime.now());
        return repository.save(record);
    }

    private BigDecimal calculateCommission(BigDecimal amount) {
        BigDecimal rate = amount.compareTo(THRESHOLD) > 0 ? HIGH_RATE : LOW_RATE;
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
