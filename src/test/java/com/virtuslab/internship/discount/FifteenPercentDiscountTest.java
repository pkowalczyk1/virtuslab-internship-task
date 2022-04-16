package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentDiscountTest {

    @Test
    void shouldApplyDiscountIfAtLeast3GrainProducts() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> entries = new ArrayList<>();
        entries.add(new ReceiptEntry(bread, 1));
        entries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(entries);
        var discount = new FifteenPercentDiscount();
        var expectedPrice = cereals.price().multiply(BigDecimal.valueOf(2))
                .add(bread.price())
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var afterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedPrice, afterDiscount.totalPrice());
        assertEquals(1, afterDiscount.discounts().size());
    }

    @Test
    void shouldNotApplyIfFewerThan3GrainProducts() {
        // Given
        var productDb = new ProductDb();
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> entries = new ArrayList<>();
        entries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(entries);
        var discount = new FifteenPercentDiscount();
        var expectedPrice = cereals.price().multiply(BigDecimal.valueOf(2));

        //When
        var afterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedPrice, afterDiscount.totalPrice());
        assertEquals(0, afterDiscount.discounts().size());
    }
}
