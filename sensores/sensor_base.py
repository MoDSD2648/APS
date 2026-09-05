import time
from datetime import datetime, timedelta, timezone
import requests


def agora_brasilia() -> str:
    """Produz o timestamp ISO-8601 enviado em todas as leituras."""
    return datetime.now(timezone(timedelta(hours=-3))).isoformat()


def enviar_leitura(url: str, dados: dict) -> bool:
    """Faz o POST e trata falhas sem encerrar o sensor periódico."""
    try:
        resposta = requests.post(url, json=dados, timeout=5)
        print(f"[{dados['timestamp']}] POST {url} -> {resposta.status_code} {resposta.text}")
        return resposta.ok
    except requests.RequestException as erro:
        print(f"Falha de conexão com {url}: {erro}")
        return False


def executar_gerador(sensor_id: str, url: str, gerar_payload, intervalo: int = 5) -> None:
    """Gera e envia uma nova leitura continuamente no intervalo configurado."""
    print(f"Sensor {sensor_id} iniciado. Pressione Ctrl+C para encerrar.")
    try:
        while True:
            enviar_leitura(url, gerar_payload(sensor_id, agora_brasilia()))
            time.sleep(intervalo)
    except KeyboardInterrupt:
        print("Sensor encerrado.")
