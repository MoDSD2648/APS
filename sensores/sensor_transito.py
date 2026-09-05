import random
from sensor_base import executar_gerador

URL = "http://localhost:8081/transito/intensidade"
SENSOR_ID = "TRANSITO-001"
VIAS = ["Avenida Brasil", "Avenida Paulista", "Marginal Tiete"]

def gerar_payload(sensor_id: str, timestamp: str) -> dict:
    """Simula a classificação da intensidade do tráfego em uma via."""
    return {"sensorId": sensor_id, "timestamp": timestamp, "via": random.choice(VIAS),
            "intensidade": random.choice(["BAIXA", "MEDIA", "ALTA"])}

if __name__ == "__main__":
    executar_gerador(SENSOR_ID, URL, gerar_payload)
