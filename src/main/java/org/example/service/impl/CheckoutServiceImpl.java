package org.example.service.impl;

import org.example.model.CustomUserDetails;
import org.example.entity.User;
import org.example.enums.UserTypeEnum;
import org.example.model.CheckoutRequest;
import org.example.model.CheckoutResponse;
import org.example.model.Item;
import org.example.service.CheckoutService;
import org.example.service.strategy.DiscountFactory;
import org.example.service.strategy.DiscountPercentageType;
import org.example.service.strategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final DiscountFactory discountFactory;

    public CheckoutServiceImpl(DiscountFactory discountFactory) {
        this.discountFactory = discountFactory;
    }

    public CheckoutResponse calculateDiscount(CustomUserDetails userDetails, CheckoutRequest checkoutRequest) {
        DiscountStrategy discountStrategy = getDiscountStrategy(userDetails.getUser());
        double totalBillAmount = checkoutRequest.getItems().stream().mapToDouble(Item::getPrice).sum();
        double percentageDiscount = discountStrategy != null ? discountStrategy.calculateDiscount(checkoutRequest.getItems()) : 0;
        double flatBillDiscount = Math.floor(totalBillAmount / 100) * 5;

        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setTotalAmount(totalBillAmount);
        checkoutResponse.setTotalAmountAfterDiscount(totalBillAmount - percentageDiscount - flatBillDiscount);

        return checkoutResponse;
    }

    private DiscountStrategy getDiscountStrategy(User user) {
        DiscountStrategy discountStrategy;
        if (UserTypeEnum.EMPLOYEE == user.getUserType()) {
            discountStrategy = discountFactory.getDiscountStrategy(DiscountPercentageType.EMPLOYEE_DISCOUNT);
        } else if (UserTypeEnum.AFFILIATE == user.getUserType()) {
            discountStrategy = discountFactory.getDiscountStrategy(DiscountPercentageType.AFFILIATE_DISCOUNT);
        } else if (ChronoUnit.YEARS.between(user.getUserCreationDate(), LocalDate.now()) >= 2) {
            discountStrategy = discountFactory.getDiscountStrategy(DiscountPercentageType.LOYAL_USER_DISCOUNT);
        } else {
            discountStrategy = discountFactory.getDiscountStrategy(DiscountPercentageType.NO_DISCOUNT);
        }
        return discountStrategy;
    }
}
