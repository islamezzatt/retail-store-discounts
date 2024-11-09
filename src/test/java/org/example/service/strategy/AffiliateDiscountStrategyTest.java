package org.example.service.strategy;

import org.example.model.Item;
import org.example.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AffiliateDiscountStrategyTest {

    private AffiliateDiscountStrategy affiliateDiscountStrategy;

    @BeforeEach
    void setUp() {
        affiliateDiscountStrategy = new AffiliateDiscountStrategy();
    }

    @Test
    void testCalculateDiscount_withNoGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithGroceries(false).getItems();

        double discount = affiliateDiscountStrategy.calculateDiscount(items);

        assertEquals(15.0, discount);
    }

    @Test
    void testCalculateDiscount_withSomeGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithGroceries(true).getItems();

        double discount = affiliateDiscountStrategy.calculateDiscount(items);

        assertEquals(10.0, discount);
    }

    @Test
    void testCalculateDiscount_withOnlyGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithAllGroceries().getItems();

        double discount = affiliateDiscountStrategy.calculateDiscount(items);

        assertEquals(0.0, discount);
    }

    @Test
    void testCalculateDiscount_withEmptyList() {
        List<Item> items = List.of();
        double discount = affiliateDiscountStrategy.calculateDiscount(items);

        assertEquals(0.0, discount);
    }
}
