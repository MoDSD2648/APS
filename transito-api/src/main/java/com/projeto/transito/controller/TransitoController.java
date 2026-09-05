package com.projeto.transito.controller;

import com.projeto.transito.client.CentralClient;
import com.projeto.transito.model.IntensidadeTransito;
import com.projeto.transito.model.TransportePublico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Set;

/** Ponto de entrada HTTP para mobilidade: fluxo viário e transporte público. */
@RestController
@RequestMapping("/transito")
public class TransitoController {
    private static final Set<String> INTENSIDADES = Set.of("BAIXA", "MEDIA", "ALTA");
    private final CentralClient centralClient;
    public TransitoController(CentralClient centralClient) { this.centralClient = centralClient; }

    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "UP", "service", "transito-api"); }

    /** O @RequestBody transforma o JSON da leitura viária no model correspondente. */
    @PostMapping("/intensidade")
    public ResponseEntity<?> receberIntensidade(@RequestBody IntensidadeTransito dados) {
        if (!metadadosValidos(dados.getSensorId(), dados.getTimestamp()) || vazio(dados.getVia())
                || !INTENSIDADES.contains(dados.getIntensidade())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Dados de intensidade invalidos; use BAIXA, MEDIA ou ALTA"));
        }
        System.out.println("Transito: " + dados.getVia() + " - " + dados.getIntensidade());
        return encaminhar("/ingest/transito/intensidade", dados);
    }

    @PostMapping("/transporte")
    public ResponseEntity<?> receberTransporte(@RequestBody TransportePublico dados) {
        if (!metadadosValidos(dados.getSensorId(), dados.getTimestamp()) || vazio(dados.getLinha())
                || vazio(dados.getVeiculo()) || dados.getOcupacao() < 0 || dados.getOcupacao() > 100) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Dados de transporte invalidos"));
        }
        System.out.println("Transporte: " + dados.getLinha() + " - ocupacao " + dados.getOcupacao() + "%");
        return encaminhar("/ingest/transito/transporte", dados);
    }

    private ResponseEntity<?> encaminhar(String rota, Object dados) {
        return centralClient.encaminhar(rota, dados)
                ? ResponseEntity.status(201).body(dados)
                : ResponseEntity.status(503).body(Map.of("status", "ERROR", "erro", "Servidor central indisponivel"));
    }
    private boolean metadadosValidos(String id, String timestamp) { return !vazio(id) && !vazio(timestamp); }
    private boolean vazio(String valor) { return valor == null || valor.isBlank(); }
}
