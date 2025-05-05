# Healthcare Microservices System

Modern healthcare management platform built with microservices architecture. Features secure patient data handling, appointment scheduling, pharmacy operations, and real-time analytics with event-driven communication.

---

## Current Issues

To evolve this PoC into a production-grade platform, we have identified key **issues** in our current setup and outlined **recommendations** to address each. All work is incrementally delivered via our CI/CD pipeline and progress is tracked in [SCHEDULE.md](SCHEDULE.md).

| Domain                           | Current Issue                                                                                                                                                           | Recommendation                                                                                                                                                                                                     | Schedule Step |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------- |
| **Security & AuthN/Z**           | - No mutual TLS between services<br/>- JWT secret in plaintext<br/>- Token introspection on every request (performance bottleneck)<br/>- No RBAC enforcement downstream | - Implement mTLS (Istio/Linkerd) or Spring Cloud mutual TLS + Vault-issued certs<br/>- Move secrets to Vault (HashiCorp/AWS/Azure)<br/>- Cache introspection results in Redis<br/>- Enforce `@PreAuthorize` scopes | Step 1–3      |
| **API Gateway & Rate Limiting**  | - In-memory `ConcurrentMap` for rate-limiting (single node)<br/>- Mixed authN and rate-limit code in gateway                                                            | - Use distributed rate limiter (Bucket4j + Redis or Envoy global rate-limit)<br/>- Push rate-limits into dedicated filter or sidecar                                                                               | Step 1–2      |
| **Resilience & Fault Tolerance** | - No circuit breakers, retries, or bulkheads                                                                                                                            | - Integrate Resilience4j circuit breakers, bulkheads, timeouts, and retries around all WebClient/Feign calls                                                                                                       | Step 1–5      |
| **Configuration Management**     | - Local `application.properties` per service<br/>- Secrets not versioned or auto-refreshed                                                                              | - Centralize config in Spring Cloud Config or Consul KV<br/>- Encrypt sensitive values in Vault, use `@RefreshScope`                                                                                               | Step 1–19     |
| **CI/CD & Containerization**     | - CI only compiles; no lint/tests/analysis<br/>- Dockerfiles lack multi-stage best practices<br/>- No image tagging or release strategy                                 | - Extend GitHub Actions: static analysis (Checkstyle/SpotBugs), unit/integration/contract tests, security scans<br/>- Adopt multi-stage Docker builds, non-root images, Trivy scans<br/>- Tag images by SHA/semver | Ongoing       |
| **Observability**                | - No centralized logging, metrics, or tracing<br/>- No alerts or SLOs                                                                                                   | - Ship JSON logs to ELK/EFK or Loki/Grafana<br/>- Expose Micrometer metrics + Prometheus, create Grafana dashboards<br/>- Add OpenTelemetry/Sleuth + Jaeger<br/>- Define SLOs and Alertmanager rules               | Step 1–19     |
| **Messaging & Eventing**         | - RabbitMQ unsecured; no schema versioning, retries or DLQs                                                                                                             | - Harden RabbitMQ (TLS, auth, vhosts)<br/>- Adopt a schema registry (Avro/JSON Schema); version events<br/>- Configure DLQs and retry policies                                                                     | Throughout    |
| **Data Management**              | - No saga/compensation strategy<br/>- Single DB-per-service not strictly enforced<br/>- High‑volume reads not optimized                                                 | - Implement Saga orchestration or choreography<br/>- Enforce data-per-service isolation<br/>- Add read-models (Elasticsearch/Redis) for search/pagination endpoints                                                | Step 1–19     |
| **Testing Strategy**             | - No contract/consumer tests<br/>- No end-to-end or chaos testing                                                                                                       | - Add Pact or Spring Cloud Contract tests in CI<br/>- Automate E2E flows with REST Assured/Cypress in staging<br/>- Introduce chaos experiments (Chaos Monkey)                                                     | Step 1–19     |
| **Operational Excellence**       | - No health/readiness probes<br/>- No autoscaling policies<br/>- Missing runbooks and KBs                                                                               | - Add Spring Boot Actuator health checks; configure k8s liveness/readiness<br/>- Define HPA with resource metrics<br/>- Document runbooks for common failures                                                      | Step 1–19     |

> **Note:** Each recommendation above is tracked in [SCHEDULE.md](SCHEDULE.md), and every code change—from schema updates to CI workflow tweaks—is delivered through our GitHub Actions pipeline. Branch-specific workflows build, test, containerize, scan, and publish artifacts automatically.

---

## Table of Contents

* [System Architecture](#system-architecture)

  * [Defense in Depth Security](#defense-in-depth-security)
  * [API Gateway](#api-gateway)
  * [Authentication & Authorization](#authentication--authorization)
  * [Service Registry](#service-registry)
  * [Resilience Features](#resilience-features)
* [Service Catalog](#service-catalog)
* [REST API Endpoints](#rest-api-endpoints)

  * [Patient Service](#patient-service)
  * [Appointment Service](#appointment-service)
  * [Staff Service](#staff-service)
  * [Pharmacy Service](#pharmacy-service)
  * [Inventory Service](#inventory-service)
  * [Billing & Claims Service](#billing--claims-service)
  * [Audit Logging Service](#audit-logging-service)
  * [Notification Service](#notification-service)
  * [Analytics Service](#analytics-service)
* [Event-Driven Architecture](#event-driven-architecture)

  * [Event Publishers](#event-publishers)
  * [Event Consumers](#event-consumers)

<!-- Rest of README unchanged -->


## Table of Contents
- [System Architecture](#system-architecture)
  - [Defense in Depth Security](#Defense-in-Depth-Security)
  - [API Gateway](#api-gateway)
  - [Authentication & Authorization](#authentication--authorization)
  - [Service Registry](#service-registry)
  - [Resilience Features](#resilience-features)
- [Service Catalog](#Service-Catalog)
- [REST API Endpoints](#rest-api-endpoints)
  - [Patient Service](#patient-service)
  - [Appointment Service](#appointment-service)
  - [Staff Service](#staff-service)
  - [Pharmacy Service](#pharmacy-service)
  - [Inventory Service](#inventory-service)
  - [Billing & Claims Service](#billing--claims-service)
  - [Audit Logging Service](#audit-logging-service)
  - [Notification Service](#notification-service)
  - [Analytics Service](#analytics-service)
- [Event-Driven Architecture](#event-driven-architecture)
  - [Event Publishers](#event-publishers)
  - [Event Consumers](#event-consumers)


## System Architecture

### High-Level Architecture
![System Architecture Diagram](Architecture.svg)

### Request Flow
![Request Flow](Request-flow.svg)

### Inter-Service Communication
![Service-to-Service Interaction Diagram](inter-service-communication.svg)

### Message Broker (RabbitMQ)
![RabbitMQ-message-broker Diagram](RabbitMQ-message-broker.svg)

### Defense-in-Depth Security
- **Dual Validation**:
  - API Gateway performs initial JWT validation
  - All downstream services revalidate tokens via AuthenticationService
- **Security Context Propagation**:
  - UserDetailsDto converted to Spring Security Authentication object
  - Authorities checked at both gateway and service levels
- **Security Risk Note**:
  > Warning: JWT secret currently stored in plaintext (application.properties)

### API Gateway
- **Central Entry Point**: Handles all incoming requests
- **Security Layer**:
  - JWT validation & token introspection
  - Role-based access control (RBAC)
  - Rate limiting (requests/sec per service)
- **Routing**: Forwards authorized requests to appropriate microservices
- **Integration**: Communicates with AuthenticationService via WebClient

### Authentication & Authorization
- **JWT Management**:
  - Token issuance/validation
- **Credential Storage**:
  - Encrypted user credentials
  - Role/authority mappings
- **Service Integration**:
  - Feign clients for token introspection
  - OAuth2 compliant flows

### Service Registry
- **Service Discovery**:
  - Eureka/Consul-based registry
  - Health checks and status monitoring
- **Dynamic Routing**:
  - Logical service names
  - Instance metadata tracking

### Resilience Features
- **Circuit Breaking**:
  - Resilience4J implementation
  - Fallback mechanisms
  - Automatic service recovery
- **Load Balancing**:
  - Client-side load distribution
  - Spring Cloud LoadBalancer
  - Zone-aware routing
- **Inter-Service Auth**:
  - JWT propagation between services
  - Automatic Authentication object creation

## Service Catalog

| Service                | Key Responsibilities                                                                 | Core API Endpoints                                                                                                                                                                                                 | Published Events                                                                 | Consumed Events                                                                 |
|------------------------|-------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| **Patient Service**    | Manage patient demographics, medical history, and record lifecycle                  | `GET /patients`<br>`POST /patients`<br>`GET /patients/{id}`<br>`PUT /patients/{id}`<br>`DELETE /patients/{id}`<br>`GET /patients/search`                                                                          | `PatientRegistered`<br>`PatientUpdated`                                        | -                                                                               |
| **Appointment Service**| Handle scheduling, rescheduling, and cancellation of medical appointments           | `GET /appointments`<br>`POST /appointments`<br>`GET /appointments/{id}`<br>`PUT /appointments/{id}`<br>`DELETE /appointments/{id}`<br>`POST /appointments/{id}/cancel`<br>`POST /appointments/{id}/reschedule`   | `AppointmentCreated`<br>`AppointmentUpdated`<br>`AppointmentCancelled`         | -                                                                               |
| **Staff Service**      | Manage healthcare staff profiles, availability, and assignments                     | `GET /staff`<br>`POST /staff`<br>`GET /staff/{id}`<br>`PUT /staff/{id}`<br>`DELETE /staff/{id}`<br>`GET /staff/{id}/availability`<br>`GET /staff/{id}/assignments`                                                | -                                                                               | -                                                                               |
| **Pharmacy Service**   | Process prescriptions, manage medication dispensing, and track drug inventory       | `GET /prescriptions`<br>`POST /prescriptions`<br>`GET /prescriptions/{id}`<br>`POST /prescriptions/{id}/fill`<br>`POST /medications/{id}/dispense`                                                                | `PrescriptionFilled`<br>`MedicationDispensed`                                  | -                                                                               |
| **Inventory Service**  | Monitor medical supplies, manage stock levels, and handle reordering               | `GET /inventory`<br>`POST /inventory`<br>`GET /inventory/{id}`<br>`PUT /inventory/{id}`<br>`POST /inventory/{id}/reorder`                                                                                         | `StockLow`<br>`StockReplenished`                                               | `MedicationDispensed`                                                          |
| **Billing & Claims**   | Handle invoicing, insurance claims processing, and payment reconciliation           | `GET /invoices`<br>`POST /invoices`<br>`GET /invoices/{id}`<br>`GET /claims`<br>`POST /claims`<br>`GET /claims/{id}`<br>`POST /claims/{id}/submit`<br>`POST /claims/{id}/deny`                                   | `InvoiceGenerated`<br>`ClaimSubmitted`<br>`ClaimDenied`                        | `AppointmentCreated`<br>`PatientRegistered`                                    |
| **Audit Logging**      | Maintain system-wide audit trail for compliance and security monitoring             | `GET /audit/events`<br>`GET /audit/events/{id}`                                                                                                                                                                   | -                                                                               | All domain events                                                              |
| **Notification Service**| Manage real-time alerts via SMS/email for appointments, bills, and system events    | `GET /notifications`<br>`GET /notifications/{id}`<br>`PUT /notifications/config`                                                                                                                                  | -                                                                               | `AppointmentCreated`<br>`AppointmentUpdated`<br>`InvoiceGenerated`<br>`ClaimDenied` |
| **Analytics Service**  | Provide business intelligence, reporting, and data visualization                    | `GET /analytics/dashboard`<br>`GET /analytics/reports`<br>`GET /analytics/events`                                                                                                                                 | `DashboardUpdated`<br>`ReportGenerated`                                        | All domain events                                                              |
| **API Gateway**        | Central entry point for routing, security, and request orchestration                | N/A (Infrastructure Layer)                                                                                                                                                                                        | -                                                                               | -                                                                               |
| **Authentication Service** | Manage user authentication, authorization, and JWT token lifecycle              | `POST /auth/login`<br>`POST /auth/refresh`<br>`POST /auth/introspect`                                                                                                                                             | -                                                                               | -                                                                               |


## REST API Endpoints

### Patient Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/patients                  | Retrieve all patients           |
| POST   | /api/v1/patients                  | Create new patient              |
| GET    | /api/v1/patients/{id}            | Get patient by ID               |
| PUT    | /api/v1/patients/{id}            | Update patient details          |
| DELETE | /api/v1/patients/{id}            | Delete patient record           |
| GET    | /api/v1/patients/search?criteria | Search patients with criteria   |

### Appointment Service
| Method | Endpoint                                  | Description                     |
|--------|-------------------------------------------|---------------------------------|
| GET    | /api/v1/appointments                     | List all appointments          |
| POST   | /api/v1/appointments                     | Create new appointment         |
| GET    | /api/v1/appointments/{id}                | Get appointment details        |
| PUT    | /api/v1/appointments/{id}                | Update appointment             |
| DELETE | /api/v1/appointments/{id}                | Cancel appointment             |
| POST   | /api/v1/appointments/{id}/cancel         | Cancel specific appointment    |
| POST   | /api/v1/appointments/{id}/reschedule     | Reschedule appointment         |

### Staff Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/staff                    | List all staff members         |
| POST   | /api/v1/staff                    | Create new staff record         |
| GET    | /api/v1/staff/{id}               | Get staff details by ID         |
| PUT    | /api/v1/staff/{id}               | Update staff information        |
| DELETE | /api/v1/staff/{id}               | Remove staff record             |
| GET    | /api/v1/staff/{id}/availability  | View staff availability         |
| GET    | /api/v1/staff/{id}/assignments   | Get staff assignments           |

### Pharmacy Service
| Method | Endpoint                                  | Description                     |
|--------|-------------------------------------------|---------------------------------|
| GET    | /api/v1/prescriptions                   | List all prescriptions          |
| POST   | /api/v1/prescriptions                   | Create new prescription         |
| GET    | /api/v1/prescriptions/{id}              | Get prescription details        |
| POST   | /api/v1/prescriptions/{id}/fill         | Process prescription filling    |
| POST   | /api/v1/medications/{id}/dispense       | Dispense medication             |

### Inventory Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/inventory                | List all inventory items        |
| POST   | /api/v1/inventory                | Add new inventory item          |
| GET    | /api/v1/inventory/{id}           | Get inventory item details      |
| PUT    | /api/v1/inventory/{id}           | Update inventory item           |
| POST   | /api/v1/inventory/{id}/reorder   | Trigger reorder process         |

### Billing & Claims Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/invoices                 | List all invoices              |
| POST   | /api/v1/invoices                 | Create new invoice             |
| GET    | /api/v1/invoices/{id}            | Get invoice details            |
| GET    | /api/v1/claims                   | List all insurance claims      |
| POST   | /api/v1/claims                   | Submit new claim               |
| GET    | /api/v1/claims/{id}              | Get claim details              |
| POST   | /api/v1/claims/{id}/submit       | Submit claim to insurer        |
| POST   | /api/v1/claims/{id}/deny         | Deny insurance claim           |

### Audit Logging Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/audit/events             | Retrieve all audit events       |
| GET    | /api/v1/audit/events/{id}        | Get specific audit event        |

### Notification Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/notifications            | List all notifications         |
| GET    | /api/v1/notifications/{id}       | Get notification details       |
| PUT    | /api/v1/notifications/config     | Update notification settings   |

### Analytics Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/analytics/dashboard      | Access analytics dashboard     |
| GET    | /api/v1/analytics/reports        | Generate analytical reports    |
| GET    | /api/v1/analytics/events         | Retrieve tracked events        |



## Event-Driven Architecture

### Event Publishers
| Service            | Events Published                   | Trigger Condition                          |
|--------------------|------------------------------------|--------------------------------------------|
| Patient            | PatientRegistered, PatientUpdated | Patient record creation/update            |
| Appointment        | AppointmentCreated, Updated, Cancelled | Appointment lifecycle changes         |
| Pharmacy           | PrescriptionFilled, MedicationDispensed | Medication processing events          |
| Inventory          | StockLow, StockReplenished        | Inventory threshold changes               |

### Event Consumers
| Service            | Events Consumed                   | Business Logic                             |
|--------------------|------------------------------------|--------------------------------------------|
| Inventory          | MedicationDispensed               | Decrement stock, trigger reorder           |
| Billing & Claims   | AppointmentCreated, PatientRegistered | Initialize billing records              |
| Notification       | Various appointment/billing events | Send user notifications                 |
| Audit Logging      | All domain events                 | Maintain compliance records                |
| Analytics          | All domain events                 | Update business intelligence dashboards    |

