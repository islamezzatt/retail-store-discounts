package org.example.service.strategy;

import org.example.model.Item;
import org.example.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoyalUserDiscountStrategyTest {

    private LoyalUserDiscountStrategy loyalUserDiscountStrategy;

    @BeforeEach
    void setUp() {
        loyalUserDiscountStrategy = new LoyalUserDiscountStrategy();
    }

    @Test
    void testCalculateDiscount_withNoGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithGroceries(false).getItems();

        double discount = loyalUserDiscountStrategy.calculateDiscount(items);

        assertEquals(7.5, discount);
    }

    @Test
    void testCalculateDiscount_withSomeGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithGroceries(true).getItems();

        double discount = loyalUserDiscountStrategy.calculateDiscount(items);

        assertEquals(5.0, discount);
    }

    @Test
    void testCalculateDiscount_withOnlyGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithAllGroceries().getItems();

        double discount = loyalUserDiscountStrategy.calculateDiscount(items);

        assertEquals(0.0, discount);
    }

    @Test
    void testCalculateDiscount_withEmptyList() {
        List<Item> items = List.of();
        double discount = loyalUserDiscountStrategy.calculateDiscount(items);

        assertEquals(0.0, discount);
    }
}
