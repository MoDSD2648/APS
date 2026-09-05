package com.projeto.alagamento.model;

/** Model que representa uma medição produzida por um sensor de chuva. */
public class Chuva {
    private String sensorId;
    private String timestamp;
    private double chuvaAcumuladaMm;

    public Chuva() { }
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public double getChuvaAcumuladaMm() { return chuvaAcumuladaMm; }
    public void setChuvaAcumuladaMm(double chuvaAcumuladaMm) { this.chuvaAcumuladaMm = chuvaAcumuladaMm; }
}
