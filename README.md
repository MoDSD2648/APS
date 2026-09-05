# Projeto de Monitoramento Municipal Distribuído

## Objetivo

Simular sensores municipais e demonstrar o envio, a validação, o encaminhamento e a persistência de leituras em uma arquitetura distribuída simples.

## Arquitetura

`Sensor Python → API Java do domínio → Servidor Central → SQLite`

Cada API valida sua leitura antes de encaminhá-la. O servidor central persiste os dados e consulta periodicamente a saúde das três APIs.

## Tecnologias

- Java 17
- Spring Boot e Spring Web
- Maven
- Python e `requests`
- SQLite
- Postman
- Git/GitHub

## Domínios

### 1. Alagamento e Inundação

- Chuva acumulada em milímetros.
- Nível do córrego em centímetros.
- As duas medições pertencem à mesma `alagamento-api`.

### 2. Trânsito e Transporte Público

- Intensidade do trânsito (`BAIXA`, `MEDIA` ou `ALTA`) por via.
- Linha, veículo e percentual de ocupação do transporte público.

### 3. Ocupação de Área de Manancial

- Setor monitorado, área total, área ocupada e percentual de ocupação.

Todas as leituras incluem `sensorId` e `timestamp`.

## Estrutura dos projetos

```text
APS/
├── alagamento-api/        # controller, models e client do central
├── transito-api/          # controller, models e client do central
├── manancial-api/         # controller, model e client do central
├── central-server/        # ingestão, monitoramento e SQLite
├── sensores/              # simuladores Python e sensor_base.py
└── contratos/             # contrato resumido de comunicação
```

## Rotas/endpoints

| Domínio | Método | Endpoint | Porta | Finalidade |
| --- | --- | --- | ---: | --- |
| Alagamento | POST | `/alagamento/chuva` | 8080 | Receber chuva acumulada |
| Alagamento | POST | `/alagamento/nivel-corrego` | 8080 | Receber nível do córrego |
| Alagamento | GET | `/alagamento/health` | 8080 | Saúde da API |
| Trânsito | POST | `/transito/intensidade` | 8081 | Receber intensidade por via |
| Transporte | POST | `/transito/transporte` | 8081 | Receber ocupação do transporte |
| Trânsito | GET | `/transito/health` | 8081 | Saúde da API |
| Manancial | POST | `/manancial/ocupacao` | 8082 | Receber ocupação territorial |
| Manancial | GET | `/manancial/health` | 8082 | Saúde da API |
| Central | GET | `/health` | 8090 | Saúde do servidor central |
| Central | GET | `/monitoramento` | 8090 | Estado consolidado das APIs |

## Exemplos de JSON

Chuva:

```json
{"sensorId":"CHUVA-001","timestamp":"2026-09-04T20:00:00-03:00","chuvaAcumuladaMm":42.5}
```

Nível do córrego:

```json
{"sensorId":"CORREGO-001","timestamp":"2026-09-04T20:00:00-03:00","nivelCorregoCm":135}
```

Intensidade do trânsito:

```json
{"sensorId":"TRANSITO-001","timestamp":"2026-09-04T20:00:00-03:00","via":"Avenida Brasil","intensidade":"ALTA"}
```

Transporte público:

```json
{"sensorId":"ONIBUS-012","timestamp":"2026-09-04T20:00:00-03:00","linha":"Linha 03","veiculo":"Onibus 12","ocupacao":78}
```

Manancial:

```json
{"sensorId":"MANANCIAL-001","timestamp":"2026-09-04T20:00:00-03:00","setor":"Manancial Norte","areaTotalM2":10000,"areaOcupadaM2":2650,"percentualOcupacao":26.5}
```

## Como executar

Requisito: Java 17, Maven e Python 3 instalados.

1. Inicie primeiro o servidor central:

```powershell
cd central-server
mvn spring-boot:run
```

2. Em três novos terminais, a partir da raiz, inicie as APIs:

```powershell
cd alagamento-api
.\mvnw.cmd spring-boot:run
```

```powershell
cd transito-api
.\mvnw.cmd spring-boot:run
```

```powershell
cd manancial-api
.\mvnw.cmd spring-boot:run
```

3. Prepare os sensores:

```powershell
cd sensores
python -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
```

4. Execute cada sensor em um terminal dentro de `sensores`:

```powershell
python sensor_chuva.py
python sensor_corrego.py
python sensor_transito.py
python sensor_manancial.py
```

Cada sensor envia uma leitura a cada cinco segundos. Use `Ctrl+C` para encerrá-lo.

## Persistência

O SQLite é inicializado automaticamente pelo servidor central. O banco fica em `central-server/data/municipal.db` e possui as tabelas `chuva`, `nivel_corrego`, `intensidade_transito`, `transporte_publico` e `ocupacao_manancial`. O arquivo do banco está ignorado pelo Git.

## Testes no Postman

Inicie o central e as APIs. No Postman, selecione `POST`, informe uma URL da tabela, escolha **Body → raw → JSON**, use o respectivo exemplo acima e envie. Uma leitura válida retorna HTTP `201` e o objeto recebido. Também é possível acessar `http://localhost:8090/monitoramento` com `GET` para consultar a saúde das APIs.
