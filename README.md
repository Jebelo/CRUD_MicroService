# Customer Service Microservice

REST microservice for managing customers (`Cliente`) and their addresses (`Direccion`), built with **Spring Boot 3.x / Java 21** following **Hexagonal Architecture** (Ports & Adapters).

---

GitHub profile: https://github.com/Jebelo

Repository for this project: https://github.com/Jebelo/CRUD_MicroService

## Architecture

```
com.audifarma.customer/
├── domain/              # Pure business logic — zero framework imports
│   ├── model/           # Cliente, Direccion (immutable domain entities)
│   ├── port/in/         # Input ports (use case interfaces)
│   ├── port/out/        # Output ports (repository interfaces)
│   └── exception/       # Domain exceptions
├── application/
│   └── usecase/         # Use case implementations (orchestrate ports)
└── infrastructure/
    ├── adapter/
    │   ├── in/rest/     # Controllers, DTOs (Java records), mappers
    │   └── out/persistence/ # JPA entities, Spring Data repos, adapters
    └── config/          # Spring @Bean wiring, OpenAPI config
```

**Key constraint**: `domain/` and `application/` packages must never import Spring or JPA classes.

---

## Prerequisites

| Tool | Version |
|------|---------|
| JDK | 21+ |
| Maven | 3.9+ |
| Docker | 24+ |
| Docker Compose | v2 |
| kubectl | 1.28+ (for K8s) |

---

## Run Locally (Maven)


"C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" spring-boot:run  #Due to Maven issues with Netbeans, I have to run this command locally

mvn spring-boot:run -Dspring-boot.run.profiles=dev

```bash
# Build + run all tests + coverage report
mvn clean verify

# Start the application (H2 in-memory, port 8080)
mvn spring-boot:run

# Access
# API:        http://localhost:8080/api/v1/clientes
# Swagger UI: http://localhost:8080/swagger-ui.html
# H2 Console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:customerdb) docker profile jdbc:h2:file:/app/data/customerdb
# Health:     http://localhost:8080/actuator/health
```

---

## Run with Docker

```bash
# Build image
docker build -t customer-service:latest .

# Run with compose
docker-compose up --build

# Stop
docker-compose down
```

---

## Deploy to Kubernetes

```bash
# Apply all manifests (creates namespace, configmap, deployment, service, hpa)
kubectl apply -f k8s/

# Watch rollout
kubectl rollout status deployment/customer-service -n customer

# Port-forward for local access
kubectl port-forward svc/customer-service 8080:80 -n customer

# Tear down
kubectl delete -f k8s/
```

---

## API Reference

### Create Customer
```bash
curl -X POST http://localhost:8080/api/v1/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Perez",
    "numeroDocumento": "1234567890",
    "tipoDocumento": "CC",
    "edad": 35,
    "activo": true,
    "direcciones": [
      {
        "departamento": "Antioquia",
        "ciudad": "Medellín",
        "direccionCompleta": "Calle 10 #20-30"
      }
    ]
  }'
```

### Get Customer (with addresses)
```bash
curl http://localhost:8080/api/v1/clientes/1
```

### Update Customer
```bash
curl -X PUT http://localhost:8080/api/v1/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Maria",
    "apellido": "Lopez",
    "tipoDocumento": "CC",
    "edad": 30,
    "activo": true
  }'
```

### Add Address
```bash
curl -X POST http://localhost:8080/api/v1/clientes/1/direcciones \
  -H "Content-Type: application/json" \
  -d '{
    "departamento": "Cundinamarca",
    "ciudad": "Bogotá",
    "direccionCompleta": "Carrera 7 #32-16"
  }'
```

### Remove Address
```bash
curl -X DELETE http://localhost:8080/api/v1/clientes/1/direcciones/2
```

---

## Testing

```bash
# Unit tests only
mvn test

# Unit + integration tests + coverage report
mvn verify

# Coverage report location
open target/site/jacoco/index.html
```

Coverage threshold: **80% line coverage** enforced by JaCoCo — build fails below this.

---

## Technology Decisions

| Decision | Choice | Reason |
|----------|--------|--------|
| DB | H2 in-memory | Zero-config dev/test environment |
| Architecture | Hexagonal | Domain isolated from framework; testability |
| DTOs | Java records | Immutability, conciseness (Java 21) |
| Bean wiring | `@Configuration` `@Bean` | Explicit, avoids `@Service` on domain-adjacent classes |
| Coverage | JaCoCo | Industry standard; CI-gate on <80% |
