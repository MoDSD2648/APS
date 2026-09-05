package com.projeto.central.repository;

import com.projeto.central.model.Leituras.*;
import org.springframework.stereotype.Repository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

/** Inicializa o SQLite e concentra os INSERTs, usando parâmetros para evitar SQL inseguro. */
@Repository
public class LeituraRepository {
    private static final String URL = "jdbc:sqlite:data/municipal.db";

    public LeituraRepository() {
        try {
            Files.createDirectories(Path.of("data"));
            try (Connection c = conectar(); Statement st = c.createStatement()) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS chuva (id INTEGER PRIMARY KEY AUTOINCREMENT, sensor_id TEXT NOT NULL, timestamp TEXT NOT NULL, chuva_acumulada_mm REAL NOT NULL)");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS nivel_corrego (id INTEGER PRIMARY KEY AUTOINCREMENT, sensor_id TEXT NOT NULL, timestamp TEXT NOT NULL, nivel_corrego_cm REAL NOT NULL)");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS intensidade_transito (id INTEGER PRIMARY KEY AUTOINCREMENT, sensor_id TEXT NOT NULL, timestamp TEXT NOT NULL, via TEXT NOT NULL, intensidade TEXT NOT NULL)");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS transporte_publico (id INTEGER PRIMARY KEY AUTOINCREMENT, sensor_id TEXT NOT NULL, timestamp TEXT NOT NULL, linha TEXT NOT NULL, veiculo TEXT NOT NULL, ocupacao REAL NOT NULL)");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS ocupacao_manancial (id INTEGER PRIMARY KEY AUTOINCREMENT, sensor_id TEXT NOT NULL, timestamp TEXT NOT NULL, setor TEXT NOT NULL, area_total_m2 REAL NOT NULL, area_ocupada_m2 REAL NOT NULL, percentual_ocupacao REAL NOT NULL)");
            }
        } catch (Exception erro) {
            throw new IllegalStateException("Nao foi possivel inicializar o SQLite", erro);
        }
    }

    public void salvar(Chuva d) throws SQLException { executar("INSERT INTO chuva(sensor_id,timestamp,chuva_acumulada_mm) VALUES(?,?,?)", d.sensorId(), d.timestamp(), d.chuvaAcumuladaMm()); }
    public void salvar(NivelCorrego d) throws SQLException { executar("INSERT INTO nivel_corrego(sensor_id,timestamp,nivel_corrego_cm) VALUES(?,?,?)", d.sensorId(), d.timestamp(), d.nivelCorregoCm()); }
    public void salvar(IntensidadeTransito d) throws SQLException { executar("INSERT INTO intensidade_transito(sensor_id,timestamp,via,intensidade) VALUES(?,?,?,?)", d.sensorId(), d.timestamp(), d.via(), d.intensidade()); }
    public void salvar(TransportePublico d) throws SQLException { executar("INSERT INTO transporte_publico(sensor_id,timestamp,linha,veiculo,ocupacao) VALUES(?,?,?,?,?)", d.sensorId(), d.timestamp(), d.linha(), d.veiculo(), d.ocupacao()); }
    public void salvar(OcupacaoManancial d) throws SQLException { executar("INSERT INTO ocupacao_manancial(sensor_id,timestamp,setor,area_total_m2,area_ocupada_m2,percentual_ocupacao) VALUES(?,?,?,?,?,?)", d.sensorId(), d.timestamp(), d.setor(), d.areaTotalM2(), d.areaOcupadaM2(), d.percentualOcupacao()); }

    private Connection conectar() throws SQLException { return DriverManager.getConnection(URL); }
    private void executar(String sql, Object... valores) throws SQLException {
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < valores.length; i++) ps.setObject(i + 1, valores[i]);
            ps.executeUpdate();
        }
    }
}
