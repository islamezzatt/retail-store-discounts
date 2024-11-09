package org.example.service.strategy;

import org.example.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(DiscountPercentageType.LOYAL_USER_DISCOUNT)
public class LoyalUserDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(List<Item> items) {
        return items.stream()
                .filter(item -> Boolean.FALSE.equals(item.getIsGrocery()))
                .mapToDouble(Item::getPrice)
                .sum() * 0.05;
    }
}
