package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public class DiscountGenerator {

    private final TenPercentDiscount tenPercentDiscount;
    private final FifteenPercentDiscount fifteenPercentDiscount;

    public DiscountGenerator() {
        tenPercentDiscount = new TenPercentDiscount();
        fifteenPercentDiscount = new FifteenPercentDiscount();
    }

    public Receipt applyAvailableDiscounts(Receipt receipt) {
        receipt = fifteenPercentDiscount.apply(receipt);
        receipt = tenPercentDiscount.apply(receipt);
        return receipt;
    }
}
