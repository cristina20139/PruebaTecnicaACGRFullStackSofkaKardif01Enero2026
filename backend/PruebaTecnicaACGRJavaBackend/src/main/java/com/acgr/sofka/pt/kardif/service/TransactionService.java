package com.acgr.sofka.pt.kardif.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;
import com.acgr.sofka.pt.kardif.domain.repository.TransactionRepository;
import com.acgr.sofka.pt.kardif.service.dto.TransactionResponse;

import reactor.core.publisher.Flux;
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

    public Mono<TransactionResponse> registerTransaction(BigDecimal amount) {
        BigDecimal commissionRate = determineRate(amount);
        BigDecimal commission = calculateCommission(amount, commissionRate);
        String reason = describeReason(amount, commissionRate);
        TransactionRecord record = new TransactionRecord(null, amount, commission, LocalDateTime.now());
        return repository.save(record)
                .map(saved -> TransactionResponse.from(saved, commissionRate, reason));
    }

    public Flux<TransactionResponse> getAllTransactions() {
        return repository.findAll()
                .map(record -> {
                    BigDecimal rate = determineRate(record.getAmount());
                    String reason = describeReason(record.getAmount(), rate);
                    return TransactionResponse.from(record, rate, reason);
                });
    }

    private BigDecimal calculateCommission(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal determineRate(BigDecimal amount) {
        return amount.compareTo(THRESHOLD) > 0 ? HIGH_RATE : LOW_RATE;
    }

    private String describeReason(BigDecimal amount, BigDecimal rate) {
        if (rate.compareTo(HIGH_RATE) == 0) {
            return "El monto de " + amount + " supera el umbral de " + THRESHOLD + ", por eso se aplica la tasa alta del 5%";
        }
        return "El monto de " + amount + " no supera el umbral de " + THRESHOLD + ", por eso se aplica la tasa baja del 2%";
    }
}
