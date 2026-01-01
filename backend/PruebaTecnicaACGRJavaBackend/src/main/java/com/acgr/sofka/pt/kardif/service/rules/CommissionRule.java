package com.acgr.sofka.pt.kardif.service.rules;

import java.math.BigDecimal;

public interface CommissionRule {

    boolean matches(BigDecimal amount);

    CommissionResult apply(BigDecimal amount);
}
