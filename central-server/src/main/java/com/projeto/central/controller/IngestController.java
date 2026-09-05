package com.projeto.central.controller;

import com.projeto.central.model.Leituras.*;
import com.projeto.central.repository.LeituraRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.Map;

/** Recebe somente dados já validados pelas APIs e solicita sua persistência no SQLite. */
@RestController
@RequestMapping("/ingest")
public class IngestController {
    private final LeituraRepository repository;
    public IngestController(LeituraRepository repository) { this.repository = repository; }

    @PostMapping("/alagamento/chuva")
    public ResponseEntity<?> chuva(@RequestBody Chuva d) { return salvar(() -> repository.salvar(d)); }
    @PostMapping("/alagamento/nivel-corrego")
    public ResponseEntity<?> corrego(@RequestBody NivelCorrego d) { return salvar(() -> repository.salvar(d)); }
    @PostMapping("/transito/intensidade")
    public ResponseEntity<?> intensidade(@RequestBody IntensidadeTransito d) { return salvar(() -> repository.salvar(d)); }
    @PostMapping("/transito/transporte")
    public ResponseEntity<?> transporte(@RequestBody TransportePublico d) { return salvar(() -> repository.salvar(d)); }
    @PostMapping("/manancial/ocupacao")
    public ResponseEntity<?> manancial(@RequestBody OcupacaoManancial d) { return salvar(() -> repository.salvar(d)); }

    private ResponseEntity<?> salvar(Operacao operacao) {
        try {
            operacao.executar();
            return ResponseEntity.status(201).body(Map.of("status", "STORED"));
        } catch (SQLException erro) {
            System.err.println("Erro ao persistir leitura: " + erro.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("erro", "Falha ao gravar leitura"));
        }
    }
    @FunctionalInterface private interface Operacao { void executar() throws SQLException; }
}
