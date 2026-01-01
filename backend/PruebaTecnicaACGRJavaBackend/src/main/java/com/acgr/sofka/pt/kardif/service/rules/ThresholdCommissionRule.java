package com.acgr.sofka.pt.kardif.service.rules;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

public class ThresholdCommissionRule implements CommissionRule {

    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;
    private final BigDecimal rate;
    private final String reasonTemplate;

    public ThresholdCommissionRule(BigDecimal minAmount, BigDecimal maxAmount, BigDecimal rate, String reasonTemplate) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.rate = rate;
        this.reasonTemplate = StringUtils.hasText(reasonTemplate) ? reasonTemplate : "Monto %s aplica tasa del %s";
    }

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

    @Override
    public CommissionResult apply(BigDecimal amount) {
        BigDecimal commission = amount.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
        String reason = String.format(reasonTemplate, amount, rate);
        return new CommissionResult(rate, commission, reason);
    }
}
