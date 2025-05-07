package com.donations.donations.controller;

import com.donations.donations.dto.FundraisingBoxDTO;
import com.donations.donations.model.FundraisingBox;
import com.donations.donations.service.FundraisingBoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fundraising-boxes")
public class FundraisingBoxController {

    private static final Logger logger = LoggerFactory.getLogger(FundraisingBoxController.class);
    private final FundraisingBoxService boxService;

    @Autowired
    public FundraisingBoxController(FundraisingBoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping
    public ResponseEntity<List<FundraisingBoxDTO>> getAllFundraisingBoxes() {
        logger.info("Retrieving all fundraising boxes");

        List<FundraisingBox> boxes = boxService.getAllFundraisingBoxes();

        List<FundraisingBoxDTO> boxesDTO = boxes.stream()
                .map(box -> new FundraisingBoxDTO(box.getId(), box.isAssigned(), box.isEmpty()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(boxesDTO);
    }

    @PostMapping
    public ResponseEntity<FundraisingBox> createFundraisingBox(@RequestParam String currency) {
        logger.info("Starting creation of new fundraising field with currency: {}", currency);
        FundraisingBox newBox = null;
        try {
            newBox = boxService.createFundraisingBox(currency);
            logger.info("Event created successfully. ID: {}, Currency: {}", newBox.getId(), newBox.getCurrency());
            return new ResponseEntity<>(newBox, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating new fundraising field with currency: {}", currency, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (newBox == null) {
                logger.warn("Failed to create fundraising field.");
            }
            logger.info("End of attempt to create new fundraising field.");
        }
    }

}
