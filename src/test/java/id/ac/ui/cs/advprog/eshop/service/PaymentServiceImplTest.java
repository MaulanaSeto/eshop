package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.PaymentCoD;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;

    private Order createTestOrder() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        return new Order("order1", products, System.currentTimeMillis(), "testUser");
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Order order = createTestOrder();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        doAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            p.setId(UUID.randomUUID().toString());
            return p;
        }).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "Voucher", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(payment.getId());
        assertEquals("Voucher", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testAddPaymentVoucherRejected() {
        Order order = createTestOrder();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE123456");

        doAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            p.setId(UUID.randomUUID().toString());
            return p;
        }).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "Voucher", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(payment.getId());
        assertEquals("Voucher", payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testAddPaymentCoDSuccess() {
        Order order = createTestOrder();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Main Street");
        paymentData.put("deliveryFee", "50");

        doAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            p.setId(UUID.randomUUID().toString());
            return p;
        }).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "CoD", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(payment.getId());
        assertEquals("CoD", payment.getMethod());
        assertInstanceOf(PaymentCoD.class, payment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testAddPaymentCoDRejected() {
        Order order = createTestOrder();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "-10");

        doAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            p.setId(UUID.randomUUID().toString());
            return p;
        }).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "CoD", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(payment.getId());
        assertEquals("CoD", payment.getMethod());
        assertInstanceOf(PaymentCoD.class, payment);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusUpdatesOrder() {
        Order order = createTestOrder();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        doAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            if (p.getId() == null) p.setId(UUID.randomUUID().toString());
            return p;
        }).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "Voucher", paymentData);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        verify(paymentRepository, times(2)).save(any(Payment.class)); // one for addPayment, one for setStatus
        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());

        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        updatedPayment = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        verify(paymentRepository, times(3)).save(any(Payment.class));
        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testGetPaymentAndGetAllPayments() {
        Order order = createTestOrder();
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment(UUID.randomUUID().toString(), "Voucher", order, paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "456 Another St");
        paymentData2.put("deliveryFee", "25");
        Payment payment2 = new PaymentCoD(UUID.randomUUID().toString(), order, paymentData2);

        List<Payment> paymentsList = List.of(payment1, payment2);

        when(paymentRepository.findById(payment1.getId())).thenReturn(payment1);
        when(paymentRepository.findAll()).thenReturn(paymentsList);

        Payment fetchedPayment = paymentService.getPayment(payment1.getId());
        assertNotNull(fetchedPayment);
        assertEquals(payment1.getId(), fetchedPayment.getId());

        List<Payment> allPayments = paymentService.getAllPayments();
        assertEquals(2, allPayments.size());
        assertTrue(allPayments.contains(payment1));
        assertTrue(allPayments.contains(payment2));

        verify(paymentRepository, times(1)).findById(payment1.getId());
        verify(paymentRepository, times(1)).findAll();
    }
}