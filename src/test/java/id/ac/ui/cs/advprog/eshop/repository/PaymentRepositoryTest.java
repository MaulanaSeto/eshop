package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Order dummyOrder;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product());
        dummyOrder = new Order("dummy-order", productList, 0L, "dummy-customer");
    }

    @Test
    void testSaveNewPayment() {
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("payment1", "CreditCard", dummyOrder, paymentData);

        Payment savedPayment = paymentRepository.save(payment);
        assertNotNull(savedPayment);

        Payment fetchedPayment = paymentRepository.findById("payment1");
        assertNotNull(fetchedPayment);

        assertEquals(payment.getId(), fetchedPayment.getId());
        assertEquals(payment.getMethod(), fetchedPayment.getMethod());
        assertEquals(payment.getStatus(), fetchedPayment.getStatus());
        assertEquals(payment.getPaymentData(), fetchedPayment.getPaymentData());
    }

    @Test
    void testSaveUpdateExistingPayment() {
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("payment1", "CreditCard", dummyOrder, paymentData);
        paymentRepository.save(payment);

        Map<String, String> updatedPaymentData = new HashMap<>();
        updatedPaymentData.put("voucherCode", "ESHOP12345678ABC");
        Payment updatedPayment = new Payment("payment1", "CreditCard", dummyOrder, updatedPaymentData);
        Payment savedUpdatedPayment = paymentRepository.save(updatedPayment);

        assertNotNull(savedUpdatedPayment);
        assertEquals(1, paymentRepository.findAll().size());

        Payment fetchedPayment = paymentRepository.findById("payment1");
        assertNotNull(fetchedPayment);
        assertEquals("payment1", fetchedPayment.getId());
        assertEquals("CreditCard", fetchedPayment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), fetchedPayment.getStatus());
        assertEquals(updatedPaymentData, fetchedPayment.getPaymentData());
    }

    @Test
    void testFindByIdNotFound() {
        Payment payment = paymentRepository.findById("nonexistent");
        assertNull(payment);
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData1 = new HashMap<>();
        Payment payment1 = new Payment("payment1", "CreditCard", dummyOrder, paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP12345678ABC");
        Payment payment2 = new Payment("payment2", "CreditCard", dummyOrder, paymentData2);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
        assertTrue(allPayments.stream().anyMatch(p -> p.getId().equals("payment1")));
        assertTrue(allPayments.stream().anyMatch(p -> p.getId().equals("payment2")));
    }
}