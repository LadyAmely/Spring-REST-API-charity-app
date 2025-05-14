package com.donations.donations.service.report;

import com.donations.donations.dto.FinancialReportDTO;

import java.util.List;

public interface IFinancialReportService {
    public List<FinancialReportDTO> getFinancialReport();
}
