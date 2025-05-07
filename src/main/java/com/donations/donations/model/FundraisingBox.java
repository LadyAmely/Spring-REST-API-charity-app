package com.donations.donations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Entity
@Table(name = "fundraising_box")
@Getter
@Setter
public class FundraisingBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;

    @Column(nullable = false)
    private boolean isAssigned;

    @Column(nullable = false)
    private boolean isEmpty;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private boolean isDeleted = false;


}

