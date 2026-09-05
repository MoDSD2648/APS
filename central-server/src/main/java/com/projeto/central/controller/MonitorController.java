package com.projeto.central.controller;

import com.projeto.central.service.HealthMonitor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/** Expõe a saúde do central e a visão consolidada das APIs monitoradas. */
@RestController
public class MonitorController {
    private final HealthMonitor monitor;
    public MonitorController(HealthMonitor monitor) { this.monitor = monitor; }
    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "UP", "service", "central-server"); }
    @GetMapping("/monitoramento")
    public Map<String, String> monitoramento() { return monitor.getStatus(); }
}
