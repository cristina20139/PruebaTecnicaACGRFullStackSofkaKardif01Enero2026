package com.acgr.sofka.pt.kardif.service.rules;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

/**
 * 📈 Defines a commission rule that works within a numeric threshold.
 * <p>
 * Encapsulates the threshold logic so the rest of the system can treat rules uniformly (Single Responsibility and
 * Open/Closed).
 * <p>
 * Define una regla de comisión que opera dentro de un umbral numérico, manteniendo la lógica encapsulada para que
 * el resto del sistema trate las reglas uniformemente (Responsabilidad Única y Open/Closed).
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public class ThresholdCommissionRule implements CommissionRule {

    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;
    private final BigDecimal rate;
    private final String reasonTemplate;

    /**
     * 🧱 Configures the threshold so rule creation stays centralized and easy to test.
     *
     * @param minAmount      inclusive lower bound / límite inferior inclusivo
     * @param maxAmount      exclusive upper bound / límite superior exclusivo
     * @param rate           commission rate / tasa de comisión
     * @param reasonTemplate reason template / plantilla de razón
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public ThresholdCommissionRule(BigDecimal minAmount, BigDecimal maxAmount, BigDecimal rate, String reasonTemplate) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.rate = rate;
        this.reasonTemplate = StringUtils.hasText(reasonTemplate) ? reasonTemplate : "Monto %s aplica tasa del %s";
    }

    /**
     * ✅ Checks whether the provided amount falls into this rule’s range so the service can rely on deterministic
     * behavior.
     *
     * @param amount amount to evaluate / monto a evaluar
     * @return true if within range / true si está dentro del rango
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    @Override
    public boolean matches(BigDecimal amount) {
        if (minAmount != null && amount.compareTo(minAmount) < 0) {
            return false;
        }
        if (maxAmount != null && amount.compareTo(maxAmount) >= 0) {
            return false;
        }
        return true;
    }

    /**
     * 💰 Applies the rule to calculate the commission, keeping the formula within the implementation
     * (Single Responsibility).
     *
     * @param amount transaction amount / monto
     * @return commission result / resultado de la comisión
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    @Override
    public CommissionResult apply(BigDecimal amount) {
        BigDecimal commission = amount.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
        String reason = String.format(reasonTemplate, amount, rate);
        return new CommissionResult(rate, commission, reason);
    }
}
