package com.jzzhang.ecommerce.dto;

import com.jzzhang.ecommerce.entity.Address;
import com.jzzhang.ecommerce.entity.Customer;
import com.jzzhang.ecommerce.entity.Order;
import com.jzzhang.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
