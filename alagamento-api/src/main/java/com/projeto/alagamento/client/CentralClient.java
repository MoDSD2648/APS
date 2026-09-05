package com.projeto.alagamento.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/** Encaminha leituras validadas para o servidor central, que cuida do SQLite. */
@Service
public class CentralClient {
    private final RestClient client;
    public CentralClient(@Value("${central.url}") String centralUrl) {
        this.client = RestClient.builder().baseUrl(centralUrl).build();
    }
    public boolean encaminhar(String rota, Object dados) {
        try {
            return client.post().uri(rota).body(dados).retrieve().toBodilessEntity().getStatusCode().is2xxSuccessful();
        } catch (Exception erro) {
            System.err.println("Servidor central indisponivel: " + erro.getMessage());
            return false;
        }
    }
}
