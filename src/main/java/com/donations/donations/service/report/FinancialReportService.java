package com.donations.donations.service.report;

import com.donations.donations.dto.FinancialReportDTO;
import com.donations.donations.model.Event;
import com.donations.donations.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialReportService implements IFinancialReportService{

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<FinancialReportDTO> getFinancialReport() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(event -> new FinancialReportDTO(event.getName(), event.getBalance(), event.getCurrency()))
                .collect(Collectors.toList());
    }

}
