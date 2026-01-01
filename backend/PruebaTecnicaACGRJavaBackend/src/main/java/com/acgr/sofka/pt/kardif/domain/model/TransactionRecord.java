package com.acgr.sofka.pt.kardif.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("transactions")
public class TransactionRecord {

    @Id
    private Long id;
    private BigDecimal amount;
    private BigDecimal commission;

    @Column("executed_at")
    private LocalDateTime executedAt;

    public TransactionRecord() {
        // Required for R2DBC mapping.
    }

    public TransactionRecord(Long id, BigDecimal amount, BigDecimal commission, LocalDateTime executedAt) {
        this.id = id;
        this.amount = amount;
        this.commission = commission;
        this.executedAt = executedAt;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}
