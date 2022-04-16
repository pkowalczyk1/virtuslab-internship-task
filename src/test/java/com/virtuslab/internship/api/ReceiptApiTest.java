package com.virtuslab.internship.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptApiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldCreateReceiptFromBasket() throws Exception {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var receiptGenerator = new ReceiptGenerator();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");

        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);
        cart.addProduct(milk);

        // When
        var expectedResponseBody = receiptGenerator.generate(cart);

        // Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/receipt")
                .content(new ObjectMapper().writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entries", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.discounts", Matchers.hasSize(0)))
                .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody)
        );
    }
}
