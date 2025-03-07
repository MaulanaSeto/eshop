package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentCoD extends Payment {
    public PaymentCoD(String id, Order order, Map<String, String> paymentData) {
        super(id, "CoD", order, paymentData);
    }
}