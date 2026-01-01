package com.acgr.sofka.pt.kardif.service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * 💬 Represents the input coming from the client when they request a new transaction.
 * <p>
 * Mantiene la entrada enfocada en los datos del cliente, respeta la Responsabilidad Única y deja la validación al
 * framework (Open/Closed).
 *
 * @param amount amount to process / monto a procesar
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public record TransactionRequest(@NotNull(message = "El monto es requerido")
                                 @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
                                 BigDecimal amount) {
}
