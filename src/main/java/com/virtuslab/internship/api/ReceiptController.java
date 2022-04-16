package com.virtuslab.internship.api;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.DiscountGenerator;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReceiptController {

    @PostMapping (value="/receipt", consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Receipt getReceipt(@RequestBody Basket basket) {
        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(basket);
        var discountGenerator = new DiscountGenerator();
        receipt = discountGenerator.applyAvailableDiscounts(receipt);
        return receipt;
    }

}
