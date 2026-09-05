package com.projeto.central.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Consulta periodicamente o /health das três APIs sem interromper a ingestão de dados. */
@Service
public class HealthMonitor {
    private final Map<String, String> status = new ConcurrentHashMap<>();
    private final Map<String, String> urls = Map.of(
            "alagamento-api", "http://localhost:8080/alagamento/health",
            "transito-api", "http://localhost:8081/transito/health",
            "manancial-api", "http://localhost:8082/manancial/health");

    @Scheduled(fixedRate = 10000)
    public void verificar() {
        RestClient client = RestClient.create();
        urls.forEach((servico, url) -> {
            try {
                client.get().uri(url).retrieve().toBodilessEntity();
                status.put(servico, "UP");
            } catch (Exception erro) {
                status.put(servico, "DOWN");
            }
        });
    }
    public Map<String, String> getStatus() { return new LinkedHashMap<>(status); }
}
