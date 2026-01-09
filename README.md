# 💳 FoodCore Payment

<div align="center">

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_foodcore-payment&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_foodcore-payment)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_foodcore-payment&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_foodcore-payment)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_foodcore-payment&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_foodcore-payment)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_foodcore-payment&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_foodcore-payment)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_foodcore-payment&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_foodcore-payment)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_foodcore-payment&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_foodcore-payment)

</div>

Microsserviço responsável pelo gerenciamento de pagamentos do sistema FoodCore, incluindo geração de QR Code, integração com Mercado Pago e processamento de webhooks. Desenvolvido como parte do curso de Arquitetura de Software da FIAP (Tech Challenge).

<div align="center">
  <a href="#visao-geral">Visão Geral</a> •
  <a href="#arquitetura">Arquitetura</a> •
  <a href="#infra">Infraestrutura</a> •
  <a href="#tecnologias">Tecnologias</a> •
  <a href="#debitos-tecnicos">Débitos Técnicos</a> •
  <a href="#instalacao-e-uso">Instalação e Uso</a> •
  <a href="#apis">APIs</a> •
  <a href="#contribuicao">Contribuição</a>
</div><br>

> 📽️ Vídeo de demonstração da arquitetura: [https://youtu.be/k3XbPRxmjCw](https://youtu.be/k3XbPRxmjCw)<br>

---

<h2 id="visao-geral">📋 Visão Geral</h2>

O **FoodCore Payment** é o microsserviço responsável por todo o fluxo de pagamentos:

- **Geração de QR Code**: Criação de QR Code via Mercado Pago para pagamento
- **Processamento de Webhooks**: Recebimento de notificações de pagamento
- **Validação de Status**: Consulta de status junto à adquirente
- **Expiração Automática**: Monitoramento e cancelamento de pagamentos expirados
- **Comunicação Assíncrona**: Publicação de eventos no Azure Service Bus

### Principais Recursos

| Recurso | Descrição |
|---------|-----------|
| **Geração de QR Code** | Criação via Mercado Pago API |
| **Webhooks** | Processamento de notificações de pagamento |
| **Consulta de Status** | Sincronização com adquirente |
| **Expiração** | Scheduler para cancelar pagamentos expirados (30 min) |
| **Eventos** | `PaymentApprovedEvent`, `PaymentRejectedEvent`, `PaymentExpiredEvent` |

---

<h2 id="arquitetura">🧱 Arquitetura</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### 🎯 Princípios Adotados

- **DDD**: Bounded context de pagamento isolado
- **Clean Architecture**: Domínio independente de frameworks
- **Separação de responsabilidades**: Cada camada tem responsabilidade bem definida
- **Independência de frameworks**: Domínio não depende de Spring ou outras bibliotecas
- **Testabilidade**: Lógica de negócio isolada facilita testes unitários
- **Inversão de Dependência**: Classes utilizam abstrações, nunca implementações concretas diretamente
- **Injeção de Dependência**: Classes recebem via construtor os objetos que necessitam utilizar
- **SAGA Coreografada**: Comunicação assíncrona via eventos
- **Webhooks**: Integração com Mercado Pago

---

### 🔄 Fluxo de Pagamento

1. **Geração de QR Code**
   - Recebe dados do pedido
   - Cria ordem no Mercado Pago
   - Retorna QR Code para cliente

2. **Processamento de Webhook**
   - Recebe notificação do Mercado Pago
   - Valida e atualiza status
   - Publica evento no Service Bus

3. **Expiração de Pagamentos**
   - Scheduler monitora pagamentos pendentes
   - Cancela automaticamente após 30 minutos

---

### ⚙️ Camadas da Arquitetura

| Camada | Componentes |
|--------|-------------|
| **Domínio** | `Payment`, `PaymentMethod`, `PaymentStatus`, `Money`, `OrderId` |
| **Aplicação** | `CreatePaymentQrCodeUseCase`, `ProcessPaymentNotificationUseCase`, `ProcessExpiredPaymentsUseCase` |
| **Interface** | Controllers REST, Presenters, Gateways |
| **Infraestrutura** | CosmosDB, Retrofit (Mercado Pago), Azure Service Bus, Scheduler |

---

### 🏗️ Microsserviços do Ecossistema

| Microsserviço | Responsabilidade | Repositório |
|---------------|------------------|-------------|
| **foodcore-auth** | Autenticação (Azure Function + Cognito) | [foodcore-auth](https://github.com/FIAP-SOAT-TECH-TEAM/foodcore-auth) |
| **foodcore-order** | Gerenciamento de pedidos | [foodcore-order](https://github.com/FIAP-SOAT-TECH-TEAM/foodcore-order) |
| **foodcore-payment** | Processamento de pagamentos (este repositório) | [foodcore-payment](https://github.com/FIAP-SOAT-TECH-TEAM/foodcore-payment) |
| **foodcore-catalog** | Catálogo de produtos | [foodcore-catalog](https://github.com/FIAP-SOAT-TECH-TEAM/foodcore-catalog) |

</details>

---

<h2 id="infra">🌐 Infraestrutura</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Recursos Kubernetes

| Recurso | Descrição |
|---------|-----------|
| **Deployment** | Pods com health probes, limites de recursos |
| **Service** | Exposição interna no cluster |
| **Ingress** | Roteamento: `/api/payments/*` |
| **ConfigMap** | Configurações não sensíveis |
| **Secrets** | Credenciais (Mercado Pago, Service Bus, CosmosDB) |
| **HPA** | Escalabilidade automática |

### Integrações

| Serviço | Tipo | Descrição |
|---------|------|-----------|
| **Mercado Pago** | HTTP | Geração de QR Code e consultas |
| **Azure Service Bus** | Assíncrona | Publicação de eventos |
| **Azure CosmosDB** | Síncrona | Persistência de dados |

</details>

---

<h2 id="tecnologias">🔧 Tecnologias</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Backend

- **Java 21**: Linguagem principal
- **Spring Boot 3.4**: Framework base
- **Spring Data JPA**: Persistência
- **Retrofit**: Cliente HTTP para Mercado Pago
- **MapStruct / Lombok**: Produtividade

### Banco de Dados

- **Azure CosmosDB**: Banco NoSQL para pagamentos

### Mensageria

- **Azure Service Bus**: Eventos de pagamento

### Qualidade

- **SonarCloud**: Análise estática
- **JUnit 5 + Mockito**: Testes unitários
- **Cucumber**: Testes BDD

</details>

---

<h2 id="debitos-tecnicos">⚠️ Débitos Técnicos</h2>

<details>
<summary>Expandir para mais detalhes</summary>

| Débito | Descrição | Impacto |
|--------|-----------|---------|
| **Circuit Breaker Mercado Pago** | Implementar Circuit Breaker com OpenFeign + Resilience4j (atual: Retrofit) | Resiliência na comunicação com adquirente |
| **Job Kubernetes de Expiração** | Migrar @Scheduler para Kubernetes CronJob/Azure Function | Desacopla responsabilidade e melhora escalabilidade |
| **Transactional Outbox Pattern** | Implementar padrão para evitar escrita duplicada na SAGA | Consistência eventual garantida |
| **Microsserviço de Webhooks** | Criar MS dedicado para webhooks publicando na fila do pagamento | Separação de responsabilidades |
| **Workload Identity** | Usar Workload Identity para Pods (atual: Azure Key Vault Provider) | Segurança e gestão de credenciais |
| **OpenTelemetry** | Migrar de Zipkin/Micrometer para OpenTelemetry | Padronização de observabilidade |

<h2 id="limitacoes-quota">Limitações de Quota (Azure for Students)</h2>

> A assinatura **Azure for Students** impõe as seguintes restrições:
>
> - **Região**: Brazil South não está disponível. Utilizamos **South Central US** como alternativa
>
> - **Quota de VMs**: Apenas **2 instâncias** do SKU utilizado para o node pool do AKS, tendo um impacto direto na escalabilidade do cluster. Quando o limite é atingido, novos nós não podem ser criados e dão erro no provisionamento de workloads.
>
> ### Erro no CD dos Microsserviços
>
> Durante o deploy dos microsserviços, Pods podem ficar com status **Pending** e o seguinte erro pode aparecer:
>
> <img src=".github/images/error.jpeg" alt="Error" />
> <img src=".github/images/erroDeploy.jpeg" alt="Error" />
>
> **Causa**: O cluster atingiu o limite máximo de VMs permitido pela quota e não há recursos computacionais (CPU/memória) disponíveis nos nós existentes.
>
> **Solução**: Aguardar a liberação de recursos de outros pods e reexecutar CI + CD.

</details>

---

<h2 id="instalacao-e-uso">🚀 Instalação e Uso</h2>

### Pré-requisitos

- Java 21
- Docker e Docker Compose
- Gradle
- Conta no Mercado Pago (sandbox)

### Desenvolvimento Local

```bash
# Clonar repositório
git clone https://github.com/FIAP-SOAT-TECH-TEAM/foodcore-payment.git
cd foodcore-payment

# Subir dependências
docker-compose -f docker/docker-compose.yml up -d

# Configurar variáveis de ambiente
cp env-example .env
# Editar .env com credenciais do Mercado Pago

# Executar aplicação
./gradlew bootRun --args='--spring.profiles.active=local'

# Executar testes
./gradlew test
```

---

<h2 id="apis">📡 APIs</h2>

### Endpoints Principais

| Método | Endpoint | Ingress Port | Descrição |
|--------|----------|-----------|
| `POST` | `/payment/qrcode` | 443 (Https) | Gerar QR Code de pagamento |
| `GET` | `/payment/{orderId}` | 443 (Https) | Buscar pagamento por pedido |
| `GET` | `/payment/{orderId}/status` | 443 (Https) | Consultar status do pagamento |
| `GET` | `/payment/{orderId}/latest` | 443 (Https) | Consultar o último registro de pagamento de um pedido |
| `POST` | `/payment/webhook` | 443 (Https) | Receber notificação do Mercado Pago |

> ⚠️ A URL Base pode ser obtida via output terraform `apim_gateway_url` (foodcore-infra).

### Documentação

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI**: `http://localhost:8080/v3/api-docs`

> ⚠️ A porta pode mudar em decorrência da variável de ambiente: `SERVER_PORT`.

---

<h2 id="contribuicao">🤝 Contribuição</h2>

### Fluxo de Deploy

1. Abra um Pull Request
2. Pipeline CI executa testes e análise
3. Após aprovação, merge para `main`
4. Pipeline CD faz deploy no AKS

### Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

<div align="center">
  <strong>FIAP - Pós-graduação em Arquitetura de Software</strong><br>
  Tech Challenge
</div>
