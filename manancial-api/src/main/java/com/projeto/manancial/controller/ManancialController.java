package com.projeto.manancial.controller;

import com.projeto.manancial.client.CentralClient;
import com.projeto.manancial.model.OcupacaoManancial;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/** Recebe as medições de ocupação territorial do domínio de mananciais. */
@RestController
@RequestMapping("/manancial")
public class ManancialController {
    private final CentralClient centralClient;
    public ManancialController(CentralClient centralClient) { this.centralClient = centralClient; }

    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "UP", "service", "manancial-api"); }

    /** O @RequestBody converte o JSON; após validar, a rota encaminha o objeto ao central. */
    @PostMapping("/ocupacao")
    public ResponseEntity<?> receberOcupacao(@RequestBody OcupacaoManancial dados) {
        if (vazio(dados.getSensorId()) || vazio(dados.getTimestamp()) || vazio(dados.getSetor())
                || dados.getAreaTotalM2() <= 0 || dados.getAreaOcupadaM2() < 0
                || dados.getAreaOcupadaM2() > dados.getAreaTotalM2()
                || dados.getPercentualOcupacao() < 0 || dados.getPercentualOcupacao() > 100) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Dados de ocupacao do manancial invalidos"));
        }
        System.out.println("Manancial: " + dados.getSetor() + " - " + dados.getPercentualOcupacao() + "%");
        return centralClient.encaminhar(dados)
                ? ResponseEntity.status(201).body(dados)
                : ResponseEntity.status(503).body(Map.of("status", "ERROR", "erro", "Servidor central indisponivel"));
    }
    private boolean vazio(String valor) { return valor == null || valor.isBlank(); }
}
