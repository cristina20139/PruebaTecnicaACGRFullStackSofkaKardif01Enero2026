package com.acgr.sofka.pt.kardif.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 🗂️ Captures the transaction rule configuration so validation and defaults live in one place.
 * <p>
 * Keeps responsibility focused on configuration (Single Responsibility), exposes a simple getter to remain open to
 * newcomers (Open/Closed), and lets other services depend on this abstraction instead of the configuration system
 * directly (Dependency Inversion).
 * <p>
 * Captura la configuración de reglas de transacción para que la validación y los valores por defecto vivan en un
 * solo lugar. Mantiene la responsabilidad centrada en la configuración, expone un getter sencillo y permite que otros
 * servicios dependan de esta abstracción en lugar del sistema de configuración (Inversión de Dependencias).
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@ConfigurationProperties(prefix = "transaction")
public class TransactionRulesProperties {

    private final List<RuleDefinition> rules = new ArrayList<>();

    /**
     * 📜 Exposes the configured rule definitions so the wiring layer can stay unaware of how the list is built.
     * <p>
     * Mantiene la lista abierta para nuevas reglas y respeta la Responsabilidad Única de la clase.
     *
     * @return the configured rules / las reglas configuradas
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public List<RuleDefinition> getRules() {
        return rules;
    }

    /**
     * 🧩 Holds the details for a single commission rule so the configuration remains anemic and easy to reason about.
     * <p>
     * Mantiene los detalles de una sola regla, evita agregar lógica adicional y deja la clase abierta para más campos.
     */
    public static class RuleDefinition {
        private BigDecimal minAmount;
        private BigDecimal maxAmount;
        private BigDecimal rate;
        private String reasonTemplate;

        /**
         * 📏 Getter for the minimum amount boundary, keeping the bean dumb and focused only on data.
         *
         * @return min amount boundary / límite mínimo
         * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
         * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
         */
        public BigDecimal getMinAmount() {
            return minAmount;
        }

        /**
         * 🛠️ Setter for the minimum amount boundary so the config binder can populate it without side effects.
         *
         * @param minAmount minimum amount / monto mínimo
         */
        public void setMinAmount(BigDecimal minAmount) {
            this.minAmount = minAmount;
        }

        /**
         * 📏 Getter for the maximum amount boundary; kept simple to stay open for future validations.
         *
         * @return max amount boundary / límite máximo
         * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
         * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
         */
        public BigDecimal getMaxAmount() {
            return maxAmount;
        }

        /**
         * 🛠️ Setter for the maximum amount boundary so the property binder can supply the value.
         *
         * @param maxAmount maximum amount / monto máximo
         */
        public void setMaxAmount(BigDecimal maxAmount) {
            this.maxAmount = maxAmount;
        }

        /**
         * 💰 Getter for the rate; a simple accessor keeps the configuration open for alternative rate strategies.
         *
         * @return commission rate / tasa de comisión
         * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
         * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
         */
        public BigDecimal getRate() {
            return rate;
        }

        /**
         * 🛠️ Setter for the rate to let the binder inject different percentages without custom code.
         *
         * @param rate commission rate / tasa de comisión
         */
        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }

        /**
         * 📜 Getter for the reason template so that downstream messages can reuse the same format.
         *
         * @return reason template / plantilla de razón
         * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
         * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
         */
        public String getReasonTemplate() {
            return reasonTemplate;
        }

        /**
         * 🛠️ Setter for the reason template so the configuration binder can override messaging without logic.
         *
         * @param reasonTemplate reason template / plantilla de razón
         */
        public void setReasonTemplate(String reasonTemplate) {
            this.reasonTemplate = reasonTemplate;
        }
    }
}
