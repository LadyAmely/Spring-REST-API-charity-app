package com.donations.donations.repository;

import com.donations.donations.model.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {
}
