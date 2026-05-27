# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Customer management microservice (Java/Spring Boot 3.x) built for a technical assessment. Handles **Cliente** (customer) entities with one-to-many **Dirección** (address) relationships. Designed to run in Kubernetes.

### Domain Models

**Cliente**: `nombre`, `apellido`, `numeroDocumento`, `tipoDocumento` (Strings), `edad` (Integer), `activo` (Boolean)  
**Direccion**: `departamento`, `ciudad`, `direccionCompleta` (Strings) — belongs to one Cliente

### API Operations
- Create customer (with optional addresses)
- Update customer data
- Query customer with their addresses
- Add address to a customer
- Remove address from a customer

---

## Tech Stack

- **Java 21** + **Spring Boot 3.x**
- **Maven** for dependency management
- **H2 Database** (in-memory, configured via `application.yml`)
- **Springdoc OpenAPI** — Swagger UI at `/swagger-ui.html`
- **Spring Boot Actuator** — health endpoint at `/actuator/health`
- **JUnit 5** + **Mockito** for unit tests
- **MockMvc** / **WebTestClient** for integration tests
- **JaCoCo** for coverage reports (>80% required)

---

## Architecture: Hexagonal (Ports & Adapters)

```
src/main/java/com/audifarma/customer/
├── domain/                     # Pure business logic — NO framework imports
│   ├── model/                  # Entities: Cliente, Direccion
│   ├── port/
│   │   ├── in/                 # Input ports (use case interfaces)
│   │   └── out/                # Output ports (repository interfaces)
│   └── exception/              # Domain-specific exceptions
├── application/                # Use case implementations (orchestration only)
│   └── usecase/
├── infrastructure/
│   ├── adapter/
│   │   ├── in/rest/            # Controllers, DTOs (Java records), mappers
│   │   └── out/persistence/    # JPA entities, repositories, mappers
│   └── config/                 # Spring beans, OpenAPI config
```

**Key rule**: `domain/` and `application/` must never import Spring or JPA. All framework dependencies stay in `infrastructure/`.

DTOs must be Java **records** (immutable). Domain entities are plain Java classes (no JPA annotations in domain layer).

---

## Build & Run Commands

### Prerequisites
- JDK 21
- Maven 3.9+
- Docker & Docker Compose (for container mode)
- kubectl (for Kubernetes deployment)

### Local Development (Maven)
```bash
# Build and run tests
mvn clean verify

# Run the application
mvn spring-boot:run

# Run only unit tests
mvn test

# Run only integration tests
mvn verify -P integration-tests

# Run a single test class
mvn test -Dtest=ClienteUseCaseTest

# Run a single test method
mvn test -Dtest=ClienteUseCaseTest#shouldCreateCliente

# Generate JaCoCo coverage report (target/site/jacoco/index.html)
mvn verify jacoco:report
```

### Docker
```bash
# Build image
docker build -t customer-service:latest .

# Run with docker-compose
docker-compose up --build

# Stop
docker-compose down
```

### Kubernetes
```bash
# Apply all manifests
kubectl apply -f k8s/

# Check deployment status
kubectl rollout status deployment/customer-service

# Remove
kubectl delete -f k8s/
```

---

## Key Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/clientes` | Create customer |
| GET | `/api/v1/clientes/{id}` | Get customer with addresses |
| PUT | `/api/v1/clientes/{id}` | Update customer |
| POST | `/api/v1/clientes/{id}/direcciones` | Add address |
| DELETE | `/api/v1/clientes/{id}/direcciones/{dirId}` | Remove address |
| GET | `/actuator/health` | Health probe (liveness & readiness in K8s) |
| GET | `/swagger-ui.html` | OpenAPI docs |
| GET | `/h2-console` | H2 browser console (dev only) |

---

## Infrastructure Files

| File | Purpose |
|------|---------|
| `src/main/resources/application.yml` | H2 datasource, JPA ddl-auto, Actuator, OpenAPI config |
| `Dockerfile` | Multi-stage build (Maven → JRE 21 slim) |
| `docker-compose.yml` | Single-service compose for local dev |
| `k8s/deployment.yaml` | K8s Deployment with liveness/readiness probes on `/actuator/health` |
| `k8s/service.yaml` | K8s Service (ClusterIP or NodePort) |

---

## Testing Strategy

- **Unit tests** live in `src/test/java/.../application/usecase/` — mock the output ports with Mockito, never load Spring context.
- **Integration tests** live in `src/test/java/.../infrastructure/adapter/in/rest/` — use `@SpringBootTest` + `MockMvc`, H2 is the test DB.
- JaCoCo is configured to fail the build if line coverage drops below 80%.
- Test method naming: `should<Action>When<Condition>` (e.g., `shouldThrowExceptionWhenClienteNotFound`).
