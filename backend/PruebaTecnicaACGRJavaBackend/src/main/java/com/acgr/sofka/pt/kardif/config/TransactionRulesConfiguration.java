package com.acgr.sofka.pt.kardif.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acgr.sofka.pt.kardif.service.TransactionRulesProperties;
import com.acgr.sofka.pt.kardif.service.rules.CommissionRule;
import com.acgr.sofka.pt.kardif.service.rules.ThresholdCommissionRule;

/**
 * 💼 Builds commission rule beans for the transaction domain, keeping all rule construction centralized.
 * <p>
 * Interprets configuration properties while abiding by the Single Responsibility Principle, keeps the class open to
 * alternative rule definitions (Open/Closed), and depends on abstractions so services remain decoupled
 * (Dependency Inversion).
 * <p>
 * Construye los beans de reglas de comision para la capa de transacciones, interpretando las propiedades sin mezclar
 * otro comportamiento, permanece abierto para nuevas definiciones (Open/Closed) y depende de abstracciones en lugar
 * de servicios concretos (Inversion de Dependencias).
 *
 * @author Aura Cristina Garzon Rodriguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogota DC Colombia
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TransactionRulesProperties.class)
public class TransactionRulesConfiguration {

    /**
     * 📊 Creates a list of {@link CommissionRule} instances based on configuration or defaults so that the payment
     * service can make decisions without knowing how the rules are built.
     * <p>
     * Localizes the building logic (Single Responsibility), stays open to new rule definitions, and returns the
     * {@link CommissionRule} abstraction to keep layers decoupled (Dependency Inversion).
     * <p>
     * Crea una lista de {@link CommissionRule} a partir de la configuracion o valores por defecto para que el servicio
     * de pagos pueda decidir sin conocer como se construyen las reglas.
     *
     * @param properties the configuration-backed data defining thresholds and rates / los datos respaldados por
     *                   configuracion que definen los umbrales y tasas
     * @return a list of rules ready for downstream processing / una lista lista para el procesamiento posterior
     * @author Aura Cristina Garzon Rodriguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogota DC Colombia
     */
    @Bean
    public List<CommissionRule> commissionRules(TransactionRulesProperties properties) {
        List<CommissionRule> rules = new ArrayList<>();
        if (properties.getRules().isEmpty()) {
            rules.add(new ThresholdCommissionRule(null, new BigDecimal("10000"), new BigDecimal("0.02"),
                    "El monto %s no supera el umbral de 10000, por eso se aplica la tasa baja del 2%"));
            rules.add(new ThresholdCommissionRule(new BigDecimal("10000"), null, new BigDecimal("0.05"),
                    "El monto %s supera el umbral de 10000, por eso se aplica la tasa alta del 5%"));
            return rules;
        }
        for (TransactionRulesProperties.RuleDefinition definition : properties.getRules()) {
            rules.add(new ThresholdCommissionRule(definition.getMinAmount(), definition.getMaxAmount(),
                    definition.getRate(), definition.getReasonTemplate()));
        }
        return rules;
    }
}
