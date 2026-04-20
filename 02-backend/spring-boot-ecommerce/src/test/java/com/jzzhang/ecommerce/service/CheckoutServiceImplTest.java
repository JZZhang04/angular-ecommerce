package com.jzzhang.ecommerce.service;

import com.jzzhang.ecommerce.dao.CustomerRepository;
import com.jzzhang.ecommerce.dto.Purchase;
import com.jzzhang.ecommerce.dto.PurchaseResponse;
import com.jzzhang.ecommerce.entity.Address;
import com.jzzhang.ecommerce.entity.Customer;
import com.jzzhang.ecommerce.entity.Order;
import com.jzzhang.ecommerce.entity.OrderItem;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CheckoutServiceImpl checkoutService;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutServiceImpl(customerRepository, new SimpleMeterRegistry());
    }

    @Test
    void placeOrder_returnsTrackingNumber() {
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());

        PurchaseResponse response = checkoutService.placeOrder(buildPurchase());

        assertNotNull(response.getOrderTrackingNumber());
        assertDoesNotThrow(() -> UUID.fromString(response.getOrderTrackingNumber()));
    }

    @Test
    void placeOrder_savesCustomerToRepository() {
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());

        checkoutService.placeOrder(buildPurchase());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void placeOrder_assignsOrderItemsToOrder() {
        Purchase purchase = buildPurchase();
        purchase.setOrderItems(Set.of(new OrderItem()));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());

        checkoutService.placeOrder(purchase);

        assertEquals(1, purchase.getOrder().getOrderItems().size());
    }

    @Test
    void placeOrder_eachCallGeneratesUniqueTrackingNumber() {
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());

        PurchaseResponse r1 = checkoutService.placeOrder(buildPurchase());
        PurchaseResponse r2 = checkoutService.placeOrder(buildPurchase());

        assertNotEquals(r1.getOrderTrackingNumber(), r2.getOrderTrackingNumber());
    }

    private Purchase buildPurchase() {
        Purchase purchase = new Purchase();
        purchase.setCustomer(new Customer());
        purchase.setOrder(new Order());
        purchase.setOrderItems(Set.of());
        purchase.setShippingAddress(new Address());
        purchase.setBillingAddress(new Address());
        return purchase;
    }
}
