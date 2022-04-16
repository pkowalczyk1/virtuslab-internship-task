package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.*;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        HashMap<Product, Integer> basketItems = new LinkedHashMap<>();
        for (var product : basket.getProducts()) {
            if (basketItems.containsKey(product)) {
                basketItems.put(product, basketItems.get(product) + 1);
            }
            else {
                basketItems.put(product, 1);
            }
        }

        for (var entry : basketItems.entrySet()) {
            receiptEntries.add(new ReceiptEntry(entry.getKey(), entry.getValue()));
        }

        return new Receipt(receiptEntries);
    }
}
