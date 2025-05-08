package com.donations.donations.controller;

import com.donations.donations.dto.FundraisingBoxDTO;
import com.donations.donations.logs.LogService;
import com.donations.donations.model.FundraisingBox;
import com.donations.donations.service.fundraisingBox.FundraisingBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> unregisterFundraisingBox(@PathVariable Long id) {
        boxService.unregisterFundraisingBox(id);
        return new ResponseEntity<>("The collection point has been deregistered.", HttpStatus.OK);
    }

}
