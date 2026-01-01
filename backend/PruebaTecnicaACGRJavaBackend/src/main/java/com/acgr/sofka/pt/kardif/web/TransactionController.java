package com.acgr.sofka.pt.kardif.web;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.acgr.sofka.pt.kardif.service.TransactionService;
import com.acgr.sofka.pt.kardif.service.dto.TransactionRequest;
import com.acgr.sofka.pt.kardif.service.dto.TransactionResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponse> registerTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.registerTransaction(request.amount())
                .map(TransactionResponse::from);
    }
}
