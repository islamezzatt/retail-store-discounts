package org.example.service;

import org.example.model.CustomUserDetails;
import org.example.model.CheckoutRequest;
import org.example.model.CheckoutResponse;

public interface CheckoutService {
    CheckoutResponse calculateDiscount(CustomUserDetails userDetails, CheckoutRequest checkoutRequest);
}
