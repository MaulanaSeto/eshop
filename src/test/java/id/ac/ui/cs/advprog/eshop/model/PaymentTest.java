package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    List<Order> orders;
    List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductQuantity(2);
        product1.setProductName("Sampo Cap Bambang");
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductQuantity(1);
        product2.setProductName("Sampo Cap Usep");
        this.products.add(product1);
        this.products.add(product2);

        this.orders = new ArrayList<>();
        Order order1 = new Order("bsahcbka-42el-a674-1ecp-p9ucwcf5qw03", products, 100L, "Amba");
        Order order2 = new Order("co93nkp3-d20n-tay1-fufa-fnifh3o27rp4", products, 100L, "Ngawi");
        Order order3 = new Order("owefnt4o-iht0-9u4g-6969-gopw48ght43p", products, 100L, "Rusdi");
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @Test
    void testPaymentSuccessWithValidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");

        Payment payment = new Payment("payment1", "CreditCard", orders.getFirst(), paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentRejectedWithInvalidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDVOUCHER12");

        Payment payment = new Payment("payment2", "CreditCard", orders.get(1), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentRejectedWithNoVoucher() {
        Map<String, String> paymentData = new HashMap<>();

        Payment payment = new Payment("payment3", "CreditCard", orders.get(2), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        Payment payment = new Payment("payment4", "CreditCard", orders.getFirst(), paymentData);

        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");

        Payment payment = new Payment("payment5", "CreditCard", orders.getFirst(), paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID_STATUS"));
    }
}