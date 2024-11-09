package org.example.service.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DiscountFactory {
    private final Map<String, DiscountStrategy> discountStrategyMap;


    public DiscountFactory(Map<String, DiscountStrategy> discountStrategyMap) {
        this.discountStrategyMap = discountStrategyMap;
    }

    public DiscountStrategy getDiscountStrategy(String userType) {
        return discountStrategyMap.getOrDefault(userType, null);
    }

}
