package com.donations.donations.controller;

import com.donations.donations.dto.FundraisingBoxDTO;
import com.donations.donations.logs.LogService;
import com.donations.donations.model.FundraisingBox;
import com.donations.donations.service.fundraisingBox.FundraisingBoxService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fundraising-boxes")
public class FundraisingBoxController {

    private final LogService logService;
    private final FundraisingBoxService boxService;

    @Autowired
    public FundraisingBoxController(FundraisingBoxService boxService, LogService logService) {
        this.boxService = boxService;
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<List<FundraisingBoxDTO>> getAllFundraisingBoxes() {
        logService.logInfo("Retrieving all fundraising boxes");

        List<FundraisingBox> boxes = boxService.getAllFundraisingBoxes();

        List<FundraisingBoxDTO> boxesDTO = boxes.stream()
                .map(box -> new FundraisingBoxDTO(box.getId(), box.isAssigned(), box.isEmpty()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(boxesDTO);
    }

    @PostMapping
    public ResponseEntity<FundraisingBox> createFundraisingBox(@RequestParam String currency) {
        logService.logInfo("Starting creation of new fundraising field with currency: " + currency);

        FundraisingBox newBox = boxService.createFundraisingBox(currency);
        logService.logInfo("Event created successfully. ID: " + newBox.getId() + ", Currency: " + newBox.getCurrency());

        return new ResponseEntity<>(newBox, HttpStatus.CREATED);
    }

    @PostMapping("/{fundraisingBoxId}/assign/{eventId}")
    public ResponseEntity<String> assignFundraisingBoxToEvent(
            @PathVariable Long fundraisingBoxId, @PathVariable Long eventId) {
        boxService.assignFundraisingBoxToEvent(fundraisingBoxId, eventId);
        return new ResponseEntity<>("The gathering point has been assigned to the event.", HttpStatus.OK);
    }

    @PostMapping("/{fundraisingBoxId}/add-funds")
    public ResponseEntity<FundraisingBox> addFunds(@PathVariable Long fundraisingBoxId, @RequestBody Map<String, Object> body) {
        try {
            Object amountObj = body.get("amount");
            if (amountObj instanceof Number) {
                BigDecimal amount = new BigDecimal(amountObj.toString());
                FundraisingBox box = boxService.addFunds(fundraisingBoxId, amount);
                return ResponseEntity.ok(box);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Zła wartość amount
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{fundraisingBoxId}/transfer-funds")
    public ResponseEntity<FundraisingBox> transferFunds(@PathVariable Long fundraisingBoxId, @RequestBody BigDecimal amount){
        FundraisingBox updatedBox = boxService.transferFunds(fundraisingBoxId, amount);
        return ResponseEntity.ok(updatedBox);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> unregisterFundraisingBox(@PathVariable Long id) {
        boxService.unregisterFundraisingBox(id);
        return new ResponseEntity<>("The collection point has been deregistered.", HttpStatus.OK);
    }

}
