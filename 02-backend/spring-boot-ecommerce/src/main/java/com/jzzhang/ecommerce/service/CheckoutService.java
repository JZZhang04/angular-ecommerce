package com.jzzhang.ecommerce.service;

import com.jzzhang.ecommerce.dto.Purchase;
import com.jzzhang.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
