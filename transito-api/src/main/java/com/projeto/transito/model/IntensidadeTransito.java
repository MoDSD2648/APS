package com.projeto.transito.model;

/** Model do estado do tráfego observado por um sensor em determinada via. */
public class IntensidadeTransito {
    private String sensorId;
    private String timestamp;
    private String via;
    private String intensidade;

    public IntensidadeTransito() { }
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getVia() { return via; }
    public void setVia(String via) { this.via = via; }
    public String getIntensidade() { return intensidade; }
    public void setIntensidade(String intensidade) { this.intensidade = intensidade; }
}
