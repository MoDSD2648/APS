import random
from sensor_base import executar_gerador

URL = "http://localhost:8080/alagamento/nivel-corrego"
SENSOR_ID = "CORREGO-001"

def gerar_payload(sensor_id: str, timestamp: str) -> dict:
    """Simula o nível do córrego em centímetros."""
    return {"sensorId": sensor_id, "timestamp": timestamp, "nivelCorregoCm": round(random.uniform(20, 300), 2)}

if __name__ == "__main__":
    executar_gerador(SENSOR_ID, URL, gerar_payload)
