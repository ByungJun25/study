package com.bj25.study.java.enums;

import java.math.BigDecimal;

public interface Calculable {
    BigDecimal addCommission(BigDecimal price);
    BigDecimal offerSpecialDiscount(BigDecimal price);
}
