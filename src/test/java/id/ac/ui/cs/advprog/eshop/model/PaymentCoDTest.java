package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentCoDTest {
    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setProductId("test-product-id");
        product.setProductName("Test Product");
        product.setProductQuantity(1);

        List<Product> products = new ArrayList<>();
        products.add(product);

        sampleOrder = new Order("test-order-id", products, 50L, "Test Buyer");
    }

    @Test
    void testPaymentCoDSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Valid Street");
        paymentData.put("deliveryFee", "10");

        PaymentCoD payment = new PaymentCoD("paymentCod1", sampleOrder, paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentCoDRejectedMissingAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "10");

        PaymentCoD payment = new PaymentCoD("paymentCod2", sampleOrder, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentCoDRejectedMissingDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Valid Street");

        PaymentCoD payment = new PaymentCoD("paymentCod3", sampleOrder, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentCoDRejectedInvalidAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "   ");
        paymentData.put("deliveryFee", "10");

        PaymentCoD payment = new PaymentCoD("paymentCod4", sampleOrder, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentCoDRejectedInvalidDeliveryFeeNonNumeric() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Valid Street");
        paymentData.put("deliveryFee", "abc");

        PaymentCoD payment = new PaymentCoD("paymentCod5", sampleOrder, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentCoDRejectedInvalidDeliveryFeeNegative() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Valid Street");
        paymentData.put("deliveryFee", "-5");

        PaymentCoD payment = new PaymentCoD("paymentCod6", sampleOrder, paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}