package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Order order;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.order = order;
        this.paymentData = paymentData;

        if (paymentData.containsKey("voucherCode") && isVoucherValid(paymentData.get("voucherCode"))) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isVoucherValid(String voucher) {
        if (!(voucher.startsWith("ESHOP") && voucher.matches("^[A-Za-z0-9]{16}$"))) return false;

        int counter = 0;
        for (int i = 0; i < voucher.length(); i++) if (Character.isDigit(voucher.charAt(i))) counter++;
        return counter == 8;
    }
}