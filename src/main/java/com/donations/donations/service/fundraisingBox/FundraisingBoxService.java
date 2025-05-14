package com.donations.donations.service.fundraisingBox;

import com.donations.donations.model.Event;
import com.donations.donations.model.FundraisingBox;
import com.donations.donations.repository.EventRepository;
import com.donations.donations.repository.FundraisingBoxRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;


@Service
public class FundraisingBoxService implements IFundraisingBoxService {
    private final FundraisingBoxRepository boxRepository;
    private final EventRepository eventRepository;
    private final FundraisingBoxRepository fundraisingBoxRepository;
    private final Logger log = LoggerFactory.getLogger(FundraisingBoxService.class);


    @Autowired
    public FundraisingBoxService(FundraisingBoxRepository boxRepository, EventRepository eventRepository, FundraisingBoxRepository fundraisingBoxRepository) {
        this.boxRepository = boxRepository;
        this.eventRepository = eventRepository;
        this.fundraisingBoxRepository = fundraisingBoxRepository;
    }

    @Override
    public FundraisingBox createFundraisingBox(String currency) {
        FundraisingBox newBox = new FundraisingBox();
        newBox.setAmount(BigDecimal.ZERO);
        newBox.setCurrency(currency);
        newBox.setAssigned(false);
        newBox.setEmpty(true);
        return boxRepository.save(newBox);
    }

    @Override
    public List<FundraisingBox> getAllFundraisingBoxes() {
        return boxRepository.findAll();
    }

    @Override
    public void unregisterFundraisingBox(Long id) {
        FundraisingBox fundraisingBox = boxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The collection point has been deregistered."));
        fundraisingBox.setAmount(BigDecimal.ZERO);
        fundraisingBox.setDeleted(true);
        boxRepository.save(fundraisingBox);
    }

    @Override
    public void assignFundraisingBoxToEvent(Long fundraisingBoxId, Long eventId) {
        FundraisingBox fundraisingBox = boxRepository.findById(fundraisingBoxId)
                .orElseThrow(() -> new IllegalArgumentException("Fundraising field does not exist."));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Fundraising event does not exist."));

        if (fundraisingBox.getEvent() != null) {
            throw new IllegalStateException("The collection point is already assigned to another event.");
        }

        if (!fundraisingBox.isEmpty()) {
            throw new IllegalStateException("The collection point must be empty before assigning it to an event.");
        }

        fundraisingBox.setEvent(event);
        fundraisingBox.setAssigned(true);
        boxRepository.save(fundraisingBox);
    }

    @Override
    public FundraisingBox addFunds(Long boxId, BigDecimal amount) {
        try {

            Set<String> allowedCurrencies = new HashSet<>(Arrays.asList("USD", "EUR", "GBP"));

            FundraisingBox fundraisingBox = fundraisingBoxRepository.findById(boxId)
                    .orElseThrow(() -> new EntityNotFoundException("Box not found"));
            log.info(String.format("Before adding funds: Box ID = %d, Current Amount = %s", boxId, fundraisingBox.getAmount()));
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }

            if (!allowedCurrencies.contains(fundraisingBox.getCurrency())) {
                throw new IllegalArgumentException("Currency not supported. Allowed currencies are: USD, EUR, GBP.");
            }

            fundraisingBox.setAmount(fundraisingBox.getAmount().add(amount));
            log.info(String.format("After adding funds: New Amount = %s", fundraisingBox.getAmount()));

            return fundraisingBoxRepository.save(fundraisingBox);
        } catch (Exception e) {
            log.error("Error occurred while adding funds: " + e.getMessage());
            throw e;
        }
    }


    @Override
    public FundraisingBox transferFunds(Long boxId, BigDecimal amount) {
        FundraisingBox fundraisingBox = fundraisingBoxRepository.findById(boxId).orElseThrow(()->new EntityNotFoundException("Box do not exist."));
        Event event = fundraisingBox.getEvent();

        if (fundraisingBox.getAmount().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the box.");
        }

        BigDecimal newAmount = fundraisingBox.getAmount().subtract(amount);
        fundraisingBox.setAmount(newAmount);

        event.setBalance(event.getBalance().add(amount));
        eventRepository.save(event);

        return fundraisingBox;
    }

}

