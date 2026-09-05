package com.projeto.alagamento.controller;

import com.projeto.alagamento.client.CentralClient;
import com.projeto.alagamento.model.Chuva;
import com.projeto.alagamento.model.NivelCorrego;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/** Recebe, valida e encaminha as duas leituras do domínio de alagamento. */
@RestController
@RequestMapping("/alagamento")
public class AlagamentoController {
    private final CentralClient centralClient;
    public AlagamentoController(CentralClient centralClient) { this.centralClient = centralClient; }

    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "UP", "service", "alagamento-api"); }

    /** O @RequestBody converte o JSON enviado pelo sensor em um objeto Chuva. */
    @PostMapping("/chuva")
    public ResponseEntity<?> receberChuva(@RequestBody Chuva dados) {
        if (!metadadosValidos(dados.getSensorId(), dados.getTimestamp()) || dados.getChuvaAcumuladaMm() < 0) {
            return ResponseEntity.badRequest().body(Map.of("erro", "sensorId, timestamp e chuvaAcumuladaMm valido sao obrigatorios"));
        }
        System.out.println("Chuva recebida: " + dados.getChuvaAcumuladaMm() + " mm");
        return encaminhar("/ingest/alagamento/chuva", dados);
    }

    /** Esta rota POST recebe o nível do córrego e o envia para persistência central. */
    @PostMapping("/nivel-corrego")
    public ResponseEntity<?> receberNivelCorrego(@RequestBody NivelCorrego dados) {
        if (!metadadosValidos(dados.getSensorId(), dados.getTimestamp()) || dados.getNivelCorregoCm() < 0) {
            return ResponseEntity.badRequest().body(Map.of("erro", "sensorId, timestamp e nivelCorregoCm valido sao obrigatorios"));
        }
        System.out.println("Nivel do corrego recebido: " + dados.getNivelCorregoCm() + " cm");
        return encaminhar("/ingest/alagamento/nivel-corrego", dados);
    }

    private ResponseEntity<?> encaminhar(String rota, Object dados) {
        if (!centralClient.encaminhar(rota, dados)) {
            return ResponseEntity.status(503).body(Map.of("status", "ERROR", "erro", "Servidor central indisponivel"));
        }
        return ResponseEntity.status(201).body(dados);
    }

    private boolean metadadosValidos(String sensorId, String timestamp) {
        return sensorId != null && !sensorId.isBlank() && timestamp != null && !timestamp.isBlank();
    }
}
