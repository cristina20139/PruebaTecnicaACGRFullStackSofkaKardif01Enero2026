package com.acgr.sofka.pt.kardif.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;
import com.acgr.sofka.pt.kardif.domain.repository.TransactionRepository;
import com.acgr.sofka.pt.kardif.service.dto.TransactionResponse;
import com.acgr.sofka.pt.kardif.service.rules.CommissionResult;
import com.acgr.sofka.pt.kardif.service.rules.CommissionRule;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final List<CommissionRule> rules;

    public TransactionService(TransactionRepository repository, List<CommissionRule> rules) {
        this.repository = repository;
        this.rules = rules;
    }

    public Mono<TransactionResponse> registerTransaction(BigDecimal amount) {
        CommissionResult ruleResult = applyRule(amount);
        TransactionRecord record = new TransactionRecord(null, amount, ruleResult.commission(), LocalDateTime.now());
        return repository.save(record)
                .map(saved -> TransactionResponse.from(saved, ruleResult.rate(), ruleResult.reason()));
    }

    public Flux<TransactionResponse> getAllTransactions() {
        return repository.findAll()
                .map(record -> {
                    CommissionResult ruleResult = applyRule(record.getAmount());
                    return TransactionResponse.from(record, ruleResult.rate(), ruleResult.reason());
                });
    }

    private CommissionResult applyRule(BigDecimal amount) {
        return rules.stream()
                .filter(rule -> rule.matches(amount))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No commission rule matches amount " + amount))
                .apply(amount);
    }
}
