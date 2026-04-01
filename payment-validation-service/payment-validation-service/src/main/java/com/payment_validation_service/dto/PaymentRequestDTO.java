package com.payment_validation_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequestDTO {

    @NotBlank(message = "UserId is required")
    private String userId;

    @NotNull(message = "Amount required")
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Currency required")
    private String currency;

    @NotBlank(message = "MerchantId required")
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