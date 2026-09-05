package com.projeto.alagamento.model;

/** Model que representa uma medição do nível de um córrego. */
public class NivelCorrego {
    private String sensorId;
    private String timestamp;
    private double nivelCorregoCm;

    public NivelCorrego() { }
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public double getNivelCorregoCm() { return nivelCorregoCm; }
    public void setNivelCorregoCm(double nivelCorregoCm) { this.nivelCorregoCm = nivelCorregoCm; }
}
