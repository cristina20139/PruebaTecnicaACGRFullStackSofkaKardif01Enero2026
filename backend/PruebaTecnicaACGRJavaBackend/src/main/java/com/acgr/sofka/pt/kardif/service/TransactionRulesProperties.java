package com.acgr.sofka.pt.kardif.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "transaction")
public class TransactionRulesProperties {

    private final List<RuleDefinition> rules = new ArrayList<>();

    public List<RuleDefinition> getRules() {
        return rules;
    }

    public static class RuleDefinition {
        private BigDecimal minAmount;
        private BigDecimal maxAmount;
        private BigDecimal rate;
        private String reasonTemplate;

        public BigDecimal getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(BigDecimal minAmount) {
            this.minAmount = minAmount;
        }

        public BigDecimal getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(BigDecimal maxAmount) {
            this.maxAmount = maxAmount;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }

        public String getReasonTemplate() {
            return reasonTemplate;
        }

        public void setReasonTemplate(String reasonTemplate) {
            this.reasonTemplate = reasonTemplate;
        }
    }
}
