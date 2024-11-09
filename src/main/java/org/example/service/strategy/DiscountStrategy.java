package org.example.service.strategy;

import org.example.model.Item;

import java.util.List;

public interface DiscountStrategy {
    double calculateDiscount(List<Item> items);
}
