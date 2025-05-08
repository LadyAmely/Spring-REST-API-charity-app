package com.donations.donations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "currency_exchange_rate")
@Getter
@Setter
public class CurrencyExchangeRate {

    @Id
    @Column(name = "from_currency", length = 3)
    private String fromCurrency;

    @Id
    @Column(name = "to_currency", length = 3)
    private String toCurrency;

    @Column(name = "exchange_rate", nullable = false, precision = 10, scale = 4)
    private BigDecimal exchangeRate;

}
