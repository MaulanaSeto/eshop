package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class PaymentCoD extends Payment {
    public PaymentCoD(String id, Order order, Map<String, String> paymentData) {
        super(id, "CoD", order, paymentData);

        if (paymentData.containsKey("address") && isAddressValid(paymentData.get("address"))
                && paymentData.containsKey("deliveryFee") && isDeliveryFeeValid(paymentData.get("deliveryFee"))) {
            super.setStatus(PaymentStatus.SUCCESS.getValue());
        } else {
            super.setStatus(PaymentStatus.REJECTED.getValue());
        }
    }

    private boolean isAddressValid(String addr) {
        return !addr.isBlank();
    }

    private boolean isDeliveryFeeValid(String fee) {
        try {
            return Integer.parseInt(fee) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}