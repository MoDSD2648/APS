import random
from sensor_base import executar_gerador

URL = "http://localhost:8080/alagamento/chuva"
SENSOR_ID = "CHUVA-001"

def gerar_payload(sensor_id: str, timestamp: str) -> dict:
    """Simula chuva acumulada em milímetros."""
    return {"sensorId": sensor_id, "timestamp": timestamp, "chuvaAcumuladaMm": round(random.uniform(0, 150), 2)}

if __name__ == "__main__":
    executar_gerador(SENSOR_ID, URL, gerar_payload)
