import random
from sensor_base import executar_gerador

URL = "http://localhost:8082/manancial/ocupacao"
SENSOR_ID = "MANANCIAL-001"

def gerar_payload(sensor_id: str, timestamp: str) -> dict:
    """Simula áreas total e ocupada mantendo o percentual matematicamente coerente."""
    total = round(random.uniform(5000, 20000), 2)
    percentual = round(random.uniform(5, 60), 2)
    return {"sensorId": sensor_id, "timestamp": timestamp,
            "setor": random.choice(["Manancial Norte", "Manancial Sul", "Manancial Leste"]),
            "areaTotalM2": total, "areaOcupadaM2": round(total * percentual / 100, 2),
            "percentualOcupacao": percentual}

if __name__ == "__main__":
    executar_gerador(SENSOR_ID, URL, gerar_payload)
