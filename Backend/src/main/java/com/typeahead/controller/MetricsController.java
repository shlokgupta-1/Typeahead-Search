package com.typeahead.controller;

import com.typeahead.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService
            metricsService;

    @GetMapping
    public Map<String,Object> metrics() {

        return metricsService
                .getMetrics();
    }
}