package com.acgr.sofka.pt.kardif.messaging;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;
import com.acgr.sofka.pt.kardif.service.rules.CommissionResult;

/**
 * Simple DTO that represents the payload published when a transaction executes so downstream systems stay decoupled.
 */
public record TransactionEvent(Long transactionId, BigDecimal amount, BigDecimal commission, BigDecimal rate,
        String reason, LocalDateTime executedAt) {

    public static TransactionEvent from(TransactionRecord record, CommissionResult ruleResult) {
        return new TransactionEvent(record.getId(), record.getAmount(), record.getCommission(), ruleResult.rate(),
                ruleResult.reason(), record.getExecutedAt());
    }
}
