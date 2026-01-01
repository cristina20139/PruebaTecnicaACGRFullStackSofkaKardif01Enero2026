package com.acgr.sofka.pt.kardif.service.rules;

import java.math.BigDecimal;

/**
 * 🎯 Defines the contract for commission rules so the decision logic remains swappable (Open/Closed and
 * Dependency Inversion).
 * <p>
 * Permite que las reglas de comisión sean intercambiables y respeta los principios SOLID al mantener una sola
 * responsabilidad por regla.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public interface CommissionRule {

    /**
     * ✅ Determines if the rule applies to the provided amount so the service can rely on the abstraction.
     *
     * @param amount operation amount / monto
     * @return true if the rule applies / true si la regla aplica
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    boolean matches(BigDecimal amount);

    /**
     * 📐 Calculates the commission result once the rule matches, keeping the calculation self-contained.
     *
     * @param amount operation amount / monto
     * @return resulting commission / comisión resultante
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    CommissionResult apply(BigDecimal amount);
}
