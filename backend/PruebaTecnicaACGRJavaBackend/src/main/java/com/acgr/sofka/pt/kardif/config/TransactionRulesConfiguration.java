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

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TransactionRulesProperties.class)
public class TransactionRulesConfiguration {

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
