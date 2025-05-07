package com.donations.donations.service;

import com.donations.donations.model.FundraisingBox;
import com.donations.donations.repository.FundraisingBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FundraisingBoxService {
    private final FundraisingBoxRepository boxRepository;

    @Autowired
    public FundraisingBoxService(FundraisingBoxRepository boxRepository) {
        this.boxRepository = boxRepository;
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
}
