package com.projeto.manancial.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/** Encaminha a leitura de ocupação para persistência no servidor central. */
@Service
public class CentralClient {
    private final RestClient client;
    public CentralClient(@Value("${central.url}") String centralUrl) {
        client = RestClient.builder().baseUrl(centralUrl).build();
    }
    public boolean encaminhar(Object dados) {
        try {
            return client.post().uri("/ingest/manancial/ocupacao").body(dados).retrieve().toBodilessEntity().getStatusCode().is2xxSuccessful();
        } catch (Exception erro) {
            System.err.println("Servidor central indisponivel: " + erro.getMessage());
            return false;
        }
    }
}
