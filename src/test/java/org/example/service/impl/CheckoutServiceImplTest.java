package org.example.service.impl;

import org.example.model.CustomUserDetails;
import org.example.enums.UserTypeEnum;
import org.example.model.CheckoutResponse;
import org.example.service.strategy.DiscountFactory;
import org.example.service.strategy.DiscountPercentageType;
import org.example.service.strategy.DiscountStrategy;
import org.example.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private DiscountFactory discountFactory;

    @Mock
    private DiscountStrategy discountStrategy;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;


    @Test
    void test_calculateDiscount_employee() {
        when(discountStrategy.calculateDiscount(any())).thenReturn(30.0);
        when(discountFactory.getDiscountStrategy(DiscountPercentageType.EMPLOYEE_DISCOUNT)).thenReturn(discountStrategy);

        CheckoutResponse checkoutResponse = checkoutService
                .calculateDiscount(new CustomUserDetails(TestUtil.createUser(UserTypeEnum.EMPLOYEE, LocalDate.now())), TestUtil.createCheckoutRequestWithGroceries(true));

        assertEquals(115.0, checkoutResponse.getTotalAmountAfterDiscount());
        assertEquals(150.0, checkoutResponse.getTotalAmount());
    }

    @Test
    void test_calculateDiscount_affiliate() {
        when(discountStrategy.calculateDiscount(any())).thenReturn(10.0);
        when(discountFactory.getDiscountStrategy(DiscountPercentageType.AFFILIATE_DISCOUNT)).thenReturn(discountStrategy);

        CheckoutResponse checkoutResponse = checkoutService
                .calculateDiscount(new CustomUserDetails(TestUtil.createUser(UserTypeEnum.AFFILIATE,
                        LocalDate.now())), TestUtil.createCheckoutRequestWithGroceries(true));

        assertEquals(135.0, checkoutResponse.getTotalAmountAfterDiscount());
        assertEquals(150.0, checkoutResponse.getTotalAmount());
    }

    @Test
    void test_calculateDiscount_loyalUser() {
        when(discountStrategy.calculateDiscount(any())).thenReturn(5.0);
        when(discountFactory.getDiscountStrategy(DiscountPercentageType.LOYAL_USER_DISCOUNT)).thenReturn(discountStrategy);

        CheckoutResponse checkoutResponse = checkoutService
                .calculateDiscount(new CustomUserDetails(TestUtil.createUser(UserTypeEnum.REGULAR, LocalDate.now().minusYears(3))), TestUtil.createCheckoutRequestWithGroceries(true));

        assertEquals(140.0, checkoutResponse.getTotalAmountAfterDiscount());
        assertEquals(150.0, checkoutResponse.getTotalAmount());
    }

    @Test
    void test_calculateDiscount_regularUser() {
        when(discountStrategy.calculateDiscount(any())).thenReturn(0.0);
        when(discountFactory.getDiscountStrategy(DiscountPercentageType.NO_DISCOUNT)).thenReturn(discountStrategy);

        CheckoutResponse checkoutResponse = checkoutService
                .calculateDiscount(new CustomUserDetails(TestUtil.createUser(UserTypeEnum.REGULAR, LocalDate.now().minusYears(1))), TestUtil.createCheckoutRequestWithGroceries(true));

        assertEquals(145.0, checkoutResponse.getTotalAmountAfterDiscount());
        assertEquals(150.0, checkoutResponse.getTotalAmount());
    }

}
