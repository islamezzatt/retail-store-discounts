package org.example.controller;

import org.example.model.CustomUserDetails;
import org.example.model.CheckoutRequest;
import org.example.model.CheckoutResponse;
import org.example.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bills")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/calculate-discount")
    public ResponseEntity<CheckoutResponse> calcualteDiscountForBill(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CheckoutRequest checkoutRequest) {
        CheckoutResponse checkoutResponse = checkoutService.calculateDiscount(userDetails, checkoutRequest);
        return ResponseEntity.ok(checkoutResponse);
    }
}
