package com.donations.donations.service;

import com.donations.donations.model.Event;
import com.donations.donations.model.FundraisingBox;
import com.donations.donations.repository.EventRepository;
import com.donations.donations.repository.FundraisingBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FundraisingBoxService {
    private final FundraisingBoxRepository boxRepository;
    private final EventRepository eventRepository;

    @Autowired
    public FundraisingBoxService(FundraisingBoxRepository boxRepository, EventRepository eventRepository) {
        this.boxRepository = boxRepository;
        this.eventRepository = eventRepository;
    }

    public FundraisingBox createFundraisingBox(String currency) {
        FundraisingBox newBox = new FundraisingBox();
        newBox.setAmount(BigDecimal.ZERO);
        newBox.setCurrency(currency);
        newBox.setAssigned(false);
        newBox.setEmpty(true);
        return boxRepository.save(newBox);
    }

    public List<FundraisingBox> getAllFundraisingBoxes() {
        return boxRepository.findAll();
    }

    public void unregisterFundraisingBox(Long id) {
        FundraisingBox fundraisingBox = boxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The collection point has been deregistered."));
        fundraisingBox.setDeleted(true);
        boxRepository.save(fundraisingBox);
    }

    public void assignFundraisingBoxToEvent(Long fundraisingBoxId, Long eventId) {
        FundraisingBox fundraisingBox = boxRepository.findById(fundraisingBoxId)
                .orElseThrow(() -> new IllegalArgumentException("Fundraising field does not exist."));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Fundraising event does not exist."));

        if (fundraisingBox.isAssigned()) {
            throw new IllegalStateException("The collection point is already assigned to another event.");
        }
        fundraisingBox.setEvent(event);
        fundraisingBox.setAssigned(true);
        boxRepository.save(fundraisingBox);
    }


}
