package com.stripe_provider_service.dto;

import java.math.BigDecimal;

public class StripeSessionRequestDTO {

    private BigDecimal amount;
    private String currency;
    private String merchantId;
    private String userId;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}