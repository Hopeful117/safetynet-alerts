package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildAlertController {
    private final ChildAlertService service;

    public ChildAlertController(ChildAlertService service) {
        this.service = service;
    }

    @GetMapping("/childAlert")
    public ChildAlertResponseDTO getChildAlert(@RequestParam String address) {
        return service.getChildAlertByAddress(address);
    }
}
