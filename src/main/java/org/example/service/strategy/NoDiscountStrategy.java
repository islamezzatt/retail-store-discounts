package org.example.service.strategy;

import org.example.model.Item;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(DiscountPercentageType.NO_DISCOUNT)
@Primary
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(List<Item> items) {
        return 0;
    }
}
