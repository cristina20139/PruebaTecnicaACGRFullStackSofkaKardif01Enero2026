package com.acgr.sofka.pt.kardif.web;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.acgr.sofka.pt.kardif.service.TransactionService;
import com.acgr.sofka.pt.kardif.service.dto.TransactionRequest;
import com.acgr.sofka.pt.kardif.service.dto.TransactionResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 🌊 Exposes transaction endpoints while keeping input/output transformations minimal.
 * <p>
 * Obeys Single Responsibility by serving only HTTP concerns, remains open for additional routes, and depends on
 * {@link TransactionService} so the controller never owns business logic (Dependency Inversion).
 * <p>
 * Expone endpoints de transacción manteniendo mínimas las transformaciones de entrada/salida. Cumple la
 * Responsabilidad Única atendiendo solo HTTP, permanece abierto para nuevas rutas y depende de
 * {@link TransactionService}.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * 🤝 Injects the service dependency so the controller stays a thin adapter.
     *
     * @param transactionService service handling transactions / servicio que maneja transacciones
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * 📚 Returns all transactions while keeping the response mapping delegated to the service.
     * <p>
     * Devuelve todas las transacciones manteniendo la mapeo de respuesta en el servicio.
     *
     * @return stream of transaction responses / flujo de respuestas de transacción
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    @GetMapping
    public Flux<TransactionResponse> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    /**
     * 💳 Accepts new transaction requests, validates them, and returns the created resource.
     * <p>
     * Acepta solicitudes de nuevas transacciones, las valida y responde con el recurso creado.
     *
     * @param request validated transaction data / datos de transacción validados
     * @return created transaction response / respuesta de transacción creada
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponse> registerTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.registerTransaction(request.amount());
    }
}
