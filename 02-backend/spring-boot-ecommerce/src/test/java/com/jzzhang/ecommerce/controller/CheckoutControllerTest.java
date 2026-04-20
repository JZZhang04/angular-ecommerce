package com.jzzhang.ecommerce.controller;

import com.jzzhang.ecommerce.dto.Purchase;
import com.jzzhang.ecommerce.dto.PurchaseResponse;
import com.jzzhang.ecommerce.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @InjectMocks
    private CheckoutController checkoutController;

    @Test
    void placeOrder_delegatesToServiceAndReturnsResponse() {
        Purchase purchase = new Purchase();
        PurchaseResponse expected = new PurchaseResponse("test-tracking-number");
        when(checkoutService.placeOrder(purchase)).thenReturn(expected);

        PurchaseResponse result = checkoutController.placeOrder(purchase);

        assertEquals("test-tracking-number", result.getOrderTrackingNumber());
        verify(checkoutService).placeOrder(purchase);
    }

    @Test
    void placeOrder_callsServiceExactlyOnce() {
        Purchase purchase = new Purchase();
        when(checkoutService.placeOrder(any())).thenReturn(new PurchaseResponse("uuid"));

        checkoutController.placeOrder(purchase);

        verify(checkoutService, times(1)).placeOrder(purchase);
    }
}
