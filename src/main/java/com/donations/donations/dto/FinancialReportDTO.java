package com.donations.donations.dto;

import lombok.Getter;

import java.math.BigDecimal;

public class FinancialReportDTO {

    @Getter
    private String eventName;
    private BigDecimal amount;
    private String currency;

    public FinancialReportDTO(String eventName, BigDecimal amount, String currency) {
        this.eventName = eventName;
        this.amount = amount;
        this.currency = currency;
    }
}
