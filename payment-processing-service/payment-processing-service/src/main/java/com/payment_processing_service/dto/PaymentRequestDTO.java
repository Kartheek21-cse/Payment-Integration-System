package com.payment_processing_service.dto;

import java.math.BigDecimal;

public class PaymentRequestDTO {

    private String userId;
    private BigDecimal amount;
    private String currency;
    private String merchantId;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
}