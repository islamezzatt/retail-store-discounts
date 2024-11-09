package org.example.service.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class DiscountFactoryTest {

    private DiscountFactory discountFactory;
    private DiscountStrategy affiliateDiscountStrategy;
    private DiscountStrategy employeeDiscountStrategy;
    private DiscountStrategy loyalUserDiscountStrategy;
    private DiscountStrategy noDiscountStrategy;

    @BeforeEach
    void setUp() {

        affiliateDiscountStrategy = mock(DiscountStrategy.class);
        employeeDiscountStrategy = mock(DiscountStrategy.class);
        loyalUserDiscountStrategy = mock(DiscountStrategy.class);
        noDiscountStrategy = mock(DiscountStrategy.class);

        Map<String, DiscountStrategy> discountStrategyMap = Map.of(
                DiscountPercentageType.AFFILIATE_DISCOUNT, affiliateDiscountStrategy,
                DiscountPercentageType.EMPLOYEE_DISCOUNT, employeeDiscountStrategy,
                DiscountPercentageType.LOYAL_USER_DISCOUNT, loyalUserDiscountStrategy,
                DiscountPercentageType.NO_DISCOUNT, noDiscountStrategy
        );

        discountFactory = new DiscountFactory(discountStrategyMap);
    }

    @Test
    void testGetDiscountStrategy_withAffiliateUser() {
        DiscountStrategy strategy = discountFactory.getDiscountStrategy(DiscountPercentageType.AFFILIATE_DISCOUNT);

        assertEquals(affiliateDiscountStrategy, strategy);
    }

    @Test
    void testGetDiscountStrategy_withInvalidUserType() {
        DiscountStrategy strategy = discountFactory.getDiscountStrategy("INVALID_USER_TYPE");

        assertEquals(null, strategy);
    }

    @Test
    void testGetDiscountStrategy_withEmployeeUser() {
        DiscountStrategy strategy = discountFactory.getDiscountStrategy(DiscountPercentageType.EMPLOYEE_DISCOUNT);

        assertEquals(employeeDiscountStrategy, strategy);
    }
}
