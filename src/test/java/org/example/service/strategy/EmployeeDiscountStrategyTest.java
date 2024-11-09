package org.example.service.strategy;

import org.example.model.Item;
import org.example.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeDiscountStrategyTest {

    private EmployeeDiscountStrategy employeeDiscountStrategy;

    @BeforeEach
    void setUp() {
        employeeDiscountStrategy = new EmployeeDiscountStrategy();
    }

    @Test
    void testCalculateDiscount_withNoGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithGroceries(false).getItems();

        double discount = employeeDiscountStrategy.calculateDiscount(items);

        assertEquals(45.0, discount);
    }

    @Test
    void testCalculateDiscount_withSomeGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithGroceries(true).getItems();

        double discount = employeeDiscountStrategy.calculateDiscount(items);

        assertEquals(30.0, discount);
    }

    @Test
    void testCalculateDiscount_withOnlyGroceryItems() {
        List<Item> items = TestUtil.createCheckoutRequestWithAllGroceries().getItems();

        double discount = employeeDiscountStrategy.calculateDiscount(items);

        assertEquals(0.0, discount);
    }

    @Test
    void testCalculateDiscount_withEmptyList() {
        List<Item> items = List.of();
        double discount = employeeDiscountStrategy.calculateDiscount(items);

        assertEquals(0.0, discount);
    }
}
