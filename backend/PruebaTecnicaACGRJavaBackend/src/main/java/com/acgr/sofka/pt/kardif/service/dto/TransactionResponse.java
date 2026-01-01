package com.acgr.sofka.pt.kardif.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;

import com.fasterxml.jackson.annotation.JsonFormat;

public record TransactionResponse(Long id,
                                  BigDecimal amount,
                                  BigDecimal commission,
                                  BigDecimal commissionRate,
                                  String reason,
                                  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                  LocalDateTime executedAt) {

    public static TransactionResponse from(TransactionRecord record, BigDecimal commissionRate, String reason) {
        return new TransactionResponse(
                record.getId(),
                record.getAmount(),
                record.getCommission(),
                commissionRate,
                reason,
                record.getExecutedAt()
        );
    }
}
