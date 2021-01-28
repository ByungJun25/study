package com.bj25.study.java.enums;

import java.math.BigDecimal;
import java.time.LocalDate;

public enum PaymentType implements Calculable {
    TRANSFER("계좌 이체", 0) {

        @Override
        public BigDecimal offerSpecialDiscount(BigDecimal price) {
            LocalDate now = LocalDate.now();
            BigDecimal discountPrice = BigDecimal.ZERO;

            switch (now.getMonth()) {
                case JANUARY:
                    discountPrice = price.multiply(new BigDecimal(10)).divide(ONE_HUNDRED);
                    break;
                default:
                    break;
            }

            return price.subtract(discountPrice);
        }
    },
    CREADIT_CARD("신용카드 결제", 5) {

        @Override
        public BigDecimal offerSpecialDiscount(BigDecimal price) {
            LocalDate now = LocalDate.now();
            BigDecimal discountPrice = BigDecimal.ZERO;

            switch (now.getMonth()) {
                case JUNE:
                    discountPrice = price.multiply(new BigDecimal(15)).divide(ONE_HUNDRED);
                    break;
                default:
                    break;
            }

            return price.subtract(discountPrice);
        }
    },
    PAYPAL("페이팔 결제", 10) {

        @Override
        public BigDecimal offerSpecialDiscount(BigDecimal price) {
            LocalDate now = LocalDate.now();
            BigDecimal discountPrice = BigDecimal.ZERO;

            switch (now.getMonth()) {
                case DECEMBER:
                    discountPrice = price.multiply(new BigDecimal(10)).divide(ONE_HUNDRED);
                    break;
                default:
                    break;
            }

            return price.subtract(discountPrice);
        }
    };

    private String name;
    private int commissionPercentage;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private PaymentType(String name, int commissionPercentage) {
        this.name = name;
        this.commissionPercentage = commissionPercentage;
    }

    public String getName() {
        return this.name;
    }

    public int getCommissionPercentage() {
        return this.commissionPercentage;
    }

    @Override
    public BigDecimal addCommission(BigDecimal price) {
        BigDecimal additionPrice = price.multiply(new BigDecimal(this.getCommissionPercentage())).divide(ONE_HUNDRED);
        return price.add(additionPrice);
    }

    public abstract BigDecimal offerSpecialDiscount(BigDecimal price);

}
