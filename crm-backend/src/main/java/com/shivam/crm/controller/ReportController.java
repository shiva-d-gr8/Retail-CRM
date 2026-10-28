package com.shivam.crm.controller;

import com.shivam.crm.dto.ReportSummary;
import com.shivam.crm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/summary")
    public ReportSummary getSummary() {
        return reportService.getSummary();
    }
}