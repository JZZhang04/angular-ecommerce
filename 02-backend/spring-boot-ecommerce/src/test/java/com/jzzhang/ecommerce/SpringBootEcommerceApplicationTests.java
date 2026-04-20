package com.jzzhang.ecommerce;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SpringBootEcommerceApplicationTests {

    @Test
    void mainClassIsLoadable() {
        assertDoesNotThrow(() ->
                Class.forName("com.jzzhang.ecommerce.SpringBootEcommerceApplication"));
    }
}
