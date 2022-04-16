package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class FifteenPercentDiscount implements IDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    public boolean shouldApply(Receipt receipt) {
        int grainsCount = receipt.entries().stream()
                .filter(receiptEntry -> receiptEntry.product().type() == Product.Type.GRAINS)
                .map(ReceiptEntry::quantity)
                .reduce(0, Integer::sum);

        return grainsCount >= 3;
    }
}
