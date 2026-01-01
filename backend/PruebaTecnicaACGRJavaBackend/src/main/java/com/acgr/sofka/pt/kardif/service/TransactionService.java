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

/**
 * 🔄 Coordinates the transaction lifecycle and keeps persistence logic decoupled.
 * <p>
 * Honors the Single Responsibility Principle by limiting this class to business orchestration, keeps the methods
 * open for new flows (Open/Closed), and depends on abstractions such as {@link TransactionRepository} and
 * {@link CommissionRule} (Dependency Inversion).
 * <p>
 * Coordina el ciclo de vida de transacciones manteniendo la lógica de persistencia desacoplada.
 * Respeta la Responsabilidad Única al limitarse a la orquestación, permanece abierto para nuevos flujos y depende de
 * abstracciones como {@link TransactionRepository} y {@link CommissionRule}.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final List<CommissionRule> rules;

    /**
     * 🧱 Constructor wiring the repository and rules so the service stays immutable beyond its dependencies.
     *
     * @param repository transaction repository / repositorio de transacciones
     * @param rules      commission rules / reglas de comisión
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public TransactionService(TransactionRepository repository, List<CommissionRule> rules) {
        this.repository = repository;
        this.rules = rules;
    }

    /**
     * 💳 Registers a transaction, applying the correct rule and keeping persistence separate.
     * <p>
     * Registra una transacción aplicando la regla correcta y manteniendo la persistencia separada.
     *
     * @param amount transaction amount / monto de la transacción
     * @return saved transaction response / respuesta de transacción guardada
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public Mono<TransactionResponse> registerTransaction(BigDecimal amount) {
        CommissionResult ruleResult = applyRule(amount);
        TransactionRecord record = new TransactionRecord(null, amount, ruleResult.commission(), LocalDateTime.now());
        return repository.save(record)
                .map(saved -> TransactionResponse.from(saved, ruleResult.rate(), ruleResult.reason()));
    }

    /**
     * 📚 Streams all stored transactions, recalculating commissions in a read-only pass.
     * <p>
     * Sirve todas las transacciones almacenadas recalculando las comisiones en una pasada de solo lectura.
     *
     * @return flux of transaction responses / flujo de respuestas de transacción
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public Flux<TransactionResponse> getAllTransactions() {
        return repository.findAll()
                .map(record -> {
                    CommissionResult ruleResult = applyRule(record.getAmount());
                    return TransactionResponse.from(record, ruleResult.rate(), ruleResult.reason());
                });
    }

    /**
     * 🧭 Applies the first matching rule so the decision logic stays encapsulated in one place.
     * <p>
     * Aplica la primera regla coincidente para mantener la lógica de decisión encapsulada.
     *
     * @param amount amount to evaluate / monto a evaluar
     * @return the matching commission result / resultado de comisión coincidente
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    private CommissionResult applyRule(BigDecimal amount) {
        return rules.stream()
                .filter(rule -> rule.matches(amount))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No commission rule matches amount " + amount))
                .apply(amount);
    }
}
