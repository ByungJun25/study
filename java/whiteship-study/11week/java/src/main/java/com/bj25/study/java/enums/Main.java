package com.bj25.study.java.enums;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal price = new BigDecimal(10000);

        System.out.println(getPriceByPaymentType(price, PaymentType.TRANSFER));
        System.out.println(getPriceByPaymentType(price, PaymentType.CREADIT_CARD));
        System.out.println(getPriceByPaymentType(price, PaymentType.PAYPAL));
    }

    public static BigDecimal getPriceByPaymentType(BigDecimal price, PaymentType paymentType) {
        BigDecimal result = price;
        switch (paymentType) {
            case TRANSFER:
                result = PaymentType.TRANSFER.addCommission(PaymentType.TRANSFER.offerSpecialDiscount(price));
                break;
            case CREADIT_CARD:
                result = PaymentType.CREADIT_CARD.addCommission(PaymentType.CREADIT_CARD.offerSpecialDiscount(price));
                break;
            case PAYPAL:
                result = PaymentType.PAYPAL.addCommission(PaymentType.PAYPAL.offerSpecialDiscount(price));
                break;
            default:
                break;
        }

        return result;
    }
}
