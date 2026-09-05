package com.projeto.manancial.model;

/** Model das áreas total e ocupada em um setor de manancial monitorado. */
public class OcupacaoManancial {
    private String sensorId;
    private String timestamp;
    private String setor;
    private double areaTotalM2;
    private double areaOcupadaM2;
    private double percentualOcupacao;

    public OcupacaoManancial() { }
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
    public double getAreaTotalM2() { return areaTotalM2; }
    public void setAreaTotalM2(double areaTotalM2) { this.areaTotalM2 = areaTotalM2; }
    public double getAreaOcupadaM2() { return areaOcupadaM2; }
    public void setAreaOcupadaM2(double areaOcupadaM2) { this.areaOcupadaM2 = areaOcupadaM2; }
    public double getPercentualOcupacao() { return percentualOcupacao; }
    public void setPercentualOcupacao(double percentualOcupacao) { this.percentualOcupacao = percentualOcupacao; }
}
