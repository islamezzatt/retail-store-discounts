package org.example.util;

import org.example.entity.User;
import org.example.enums.UserTypeEnum;
import org.example.model.CheckoutRequest;
import org.example.model.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static User createUser(UserTypeEnum userTypeEnum, LocalDate userCreationDate) {
        User user = new User();
        user.setUserCreationDate(userCreationDate);
        user.setUserType(userTypeEnum);
        user.setUsername("username1");
        user.setPassword("password1");
        return user;
    }

    public static CheckoutRequest createCheckoutRequestWithGroceries(boolean hasGroceries) {
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        List<Item> items = new ArrayList<>();
        Item item1 = new Item(100, false);
        Item item2 = new Item(50, false);
        if (hasGroceries) {
            item2.setIsGrocery(Boolean.TRUE);
        }
        items.add(item1);
        items.add(item2);
        checkoutRequest.setItems(items);
        return checkoutRequest;
    }

    public static CheckoutRequest createCheckoutRequestWithAllGroceries() {
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        List<Item> items = new ArrayList<>();
        Item item1 = new Item(100, true);
        Item item2 = new Item(50, true);
        items.add(item1);
        items.add(item2);
        checkoutRequest.setItems(items);
        return checkoutRequest;
    }
}
