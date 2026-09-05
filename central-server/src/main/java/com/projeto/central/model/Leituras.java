package com.projeto.central.model;

/** Contratos recebidos das APIs. Os nomes coincidem com os models de cada domínio. */
public final class Leituras {
    private Leituras() { }
    public record Chuva(String sensorId, String timestamp, double chuvaAcumuladaMm) { }
    public record NivelCorrego(String sensorId, String timestamp, double nivelCorregoCm) { }
    public record IntensidadeTransito(String sensorId, String timestamp, String via, String intensidade) { }
    public record TransportePublico(String sensorId, String timestamp, String linha, String veiculo, double ocupacao) { }
    public record OcupacaoManancial(String sensorId, String timestamp, String setor, double areaTotalM2,
                                    double areaOcupadaM2, double percentualOcupacao) { }
}
