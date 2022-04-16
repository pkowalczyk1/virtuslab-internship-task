package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountGeneratorTest {

    @Test
    void shouldApplyOnly10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var steak = productDb.getProduct("Steak");
        var pork = productDb.getProduct("Pork");
        List<ReceiptEntry> entries = new ArrayList<>();
        entries.add(new ReceiptEntry(steak, 1));
        entries.add(new ReceiptEntry(pork, 1));

        var receipt = new Receipt(entries);
        var discountGenerator = new DiscountGenerator();
        var expectedPrice = steak.price().add(pork.price()).multiply(BigDecimal.valueOf(0.9));

        // When
        var afterDiscount = discountGenerator.applyAvailableDiscounts(receipt);

        // Then
        assertEquals(expectedPrice, afterDiscount.totalPrice());
        assertEquals(1, afterDiscount.discounts().size());
    }

    @Test
    void shouldApplyOnly15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> entries = new ArrayList<>();
        entries.add(new ReceiptEntry(bread, 1));
        entries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(entries);
        var discountGenerator = new DiscountGenerator();
        var expectedPrice = cereals.price()
                .multiply(BigDecimal.valueOf(2))
                .add(bread.price())
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var afterDiscount = discountGenerator.applyAvailableDiscounts(receipt);

        // Then
        assertEquals(expectedPrice, afterDiscount.totalPrice());
        assertEquals(1, afterDiscount.discounts().size());
    }

    @Test
    void shouldApplyBothDiscounts() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var pork = productDb.getProduct("Pork");
        List<ReceiptEntry> entries = new ArrayList<>();
        entries.add(new ReceiptEntry(bread, 1));
        entries.add(new ReceiptEntry(cereals, 2));
        entries.add(new ReceiptEntry(pork, 3));

        var receipt = new Receipt(entries);
        var discountGenerator = new DiscountGenerator();
        var expectedPrice = pork.price()
                .multiply(BigDecimal.valueOf(3))
                .add(bread.price())
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85))
                .multiply(BigDecimal.valueOf(0.9));

        // When
        var afterDiscount = discountGenerator.applyAvailableDiscounts(receipt);

        // Then
        assertEquals(expectedPrice, afterDiscount.totalPrice());
        assertEquals(2, afterDiscount.discounts().size());
    }

    @Test
    void shouldApplyNoDiscount() {
        // Given
        var productDb = new ProductDb();
        var milk = productDb.getProduct("Milk");
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> entries = new ArrayList<>();
        entries.add(new ReceiptEntry(milk, 1));
        entries.add(new ReceiptEntry(cheese, 1));

        var receipt = new Receipt(entries);
        var discountGenerator = new DiscountGenerator();
        var expectedPrice = milk.price().add(cheese.price());

        // When
        var afterDiscount = discountGenerator.applyAvailableDiscounts(receipt);

        // Then
        assertEquals(expectedPrice, afterDiscount.totalPrice());
        assertEquals(0, afterDiscount.discounts().size());
    }
}
