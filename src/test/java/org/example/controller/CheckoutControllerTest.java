package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.CustomUserDetails;
import org.example.config.security.CustomUserDetailsService;
import org.example.config.security.JWTService;
import org.example.model.CheckoutRequest;
import org.example.model.CheckoutResponse;
import org.example.service.CheckoutService;
import org.example.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckoutController.class)
@Import(SecurityTestConfig.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;

    @InjectMocks
    private CheckoutController checkoutController;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CustomUserDetails userDetails;

    @Test
    @WithMockUser(username = "username1", password = "password1")
    void testCalculateDiscountForBill() throws Exception {
        CheckoutRequest checkoutRequest = TestUtil.createCheckoutRequestWithGroceries(true);
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setTotalAmount(100.0);
        checkoutResponse.setTotalAmountAfterDiscount(95.0);

        when(userDetails.getUsername()).thenReturn("username1");
        when(checkoutService.calculateDiscount(any(), any())).thenReturn(checkoutResponse);

        mockMvc.perform(post("/api/v1/bills/calculate-discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(checkoutRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(100.0))
                .andExpect(jsonPath("$.totalAmountAfterDiscount").value(95.0));


    }

}
