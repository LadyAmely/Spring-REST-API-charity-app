package com.donations.donations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, length = 20)
    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "box_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_donation_box"))
    private FundraisingBox fundraisingBox;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", foreignKey = @ForeignKey(name = "FK_event"))
    private Event event;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "from_currency", referencedColumnName = "from_currency"),
            @JoinColumn(name = "to_currency", referencedColumnName = "to_currency")
    })
    private CurrencyExchangeRate currencyExchangeRate;
}
