package com.acgr.sofka.pt.kardif.service.rules;

import java.math.BigDecimal;

/**
 * 💡 Carries the result of evaluating a commission rule so callers know the rate, amount, and reason.
 * <p>
 * Keeps the result lightweight (Single Responsibility) and open for extension while remaining a pure data carrier.
 * <p>
 * Lleva el resultado de evaluar una regla de comisión para que los llamadores conozcan tasa, monto y motivo.
 *
 * @param rate       applied rate / tasa aplicada
 * @param commission computed commission / comisión calculada
 * @param reason     human-readable reason / razón legible
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public record CommissionResult(BigDecimal rate, BigDecimal commission, String reason) {
}
