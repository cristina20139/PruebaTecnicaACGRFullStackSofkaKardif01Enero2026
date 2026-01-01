package com.acgr.sofka.pt.kardif.service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(@NotNull(message = "El monto es requerido")
                                 @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
                                 BigDecimal amount) {
}
