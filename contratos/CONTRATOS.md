# Contratos HTTP/JSON

Os contratos oficiais usam camelCase e estão exemplificados no README principal. Toda leitura exige `sensorId` e `timestamp` ISO-8601. Valores físicos e percentuais não podem ser negativos; percentuais e ocupação ficam entre 0 e 100.

Em sucesso, as APIs retornam `201 Created`; dados inválidos retornam `400 Bad Request`; indisponibilidade do servidor central retorna `503 Service Unavailable`.
