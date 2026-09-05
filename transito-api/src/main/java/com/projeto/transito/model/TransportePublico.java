package com.projeto.transito.model;

/** Model de ocupação de um veículo de transporte público. */
public class TransportePublico {
    private String sensorId;
    private String timestamp;
    private String linha;
    private String veiculo;
    private double ocupacao;

    public TransportePublico() { }
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getLinha() { return linha; }
    public void setLinha(String linha) { this.linha = linha; }
    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }
    public double getOcupacao() { return ocupacao; }
    public void setOcupacao(double ocupacao) { this.ocupacao = ocupacao; }
}
