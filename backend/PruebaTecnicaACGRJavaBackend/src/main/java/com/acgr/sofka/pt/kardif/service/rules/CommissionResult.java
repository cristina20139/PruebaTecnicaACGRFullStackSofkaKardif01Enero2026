package com.acgr.sofka.pt.kardif.service.rules;

import java.math.BigDecimal;

public record CommissionResult(BigDecimal rate, BigDecimal commission, String reason) {
}
