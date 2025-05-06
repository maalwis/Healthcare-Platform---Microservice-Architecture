# Healthcare Microservices System

Modern healthcare management platform built with microservices architecture. Features secure patient data handling, appointment scheduling, pharmacy operations, and real-time analytics with event-driven communication.

---

## Project Status & CI/CD Integration

To keep the project organized and transparent:

- **Feature Progress** is tracked in [`SERVICE_ROADMAP.md`](./SERVICE_ROADMAP.md) at the repo root.  You’ll see exactly which endpoints and event‑listeners are ✅ implemented and which are still 🚧 pending.
- **Infrastructure Progress** lives in [`CROSS_CUTTING.md`](./CROSS_CUTTING.md), detailing what security, resilience, observability, and other cross‑cutting concerns have been put in place and what remains.

Every feature and infrastructure task is delivered via a dedicated **branch‑per‑service** GitHub Actions pipeline:

1. **Build** (compile JAR once, store as artifact)
2. **Analyze & Test** (Checkstyle, SpotBugs, unit/integration tests, Pact contracts)
3. **Scan** (Trivy/Snyk vulnerability checks)
4. **Package** (multi‑stage Docker build → non‑root JRE image)
5. **Publish** (push to Docker Hub with immutable `${SERVICE_NAME}:${GITHUB_SHA}` tags)

🔗 **Explore the full CI/CD workflows** (YAML definitions & logs):  
https://github.com/maalwis/Healthcare-Platform---Microservice-Architecture-CICD


---

## Table of Contents

* [System Architecture](#system-architecture)
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

---

## System Architecture

### High-Level Architecture
![System Architecture Diagram](Architecture.svg)

---

### Request Flow
![Request Flow](Request-flow.svg)

---

### Inter-Service Communication
![Service-to-Service Interaction Diagram](inter-service-communication.svg)

---

### Message Broker (RabbitMQ)
![RabbitMQ-message-broker Diagram](RabbitMQ-message-broker.svg)

---

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

---

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

---

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

---

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

---

### Pharmacy Service
| Method | Endpoint                                  | Description                     |
|--------|-------------------------------------------|---------------------------------|
| GET    | /api/v1/prescriptions                   | List all prescriptions          |
| POST   | /api/v1/prescriptions                   | Create new prescription         |
| GET    | /api/v1/prescriptions/{id}              | Get prescription details        |
| POST   | /api/v1/prescriptions/{id}/fill         | Process prescription filling    |
| POST   | /api/v1/medications/{id}/dispense       | Dispense medication             |

---

### Inventory Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/inventory                | List all inventory items        |
| POST   | /api/v1/inventory                | Add new inventory item          |
| GET    | /api/v1/inventory/{id}           | Get inventory item details      |
| PUT    | /api/v1/inventory/{id}           | Update inventory item           |
| POST   | /api/v1/inventory/{id}/reorder   | Trigger reorder process         |

--- 

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

--- 

### Audit Logging Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/audit/events             | Retrieve all audit events       |
| GET    | /api/v1/audit/events/{id}        | Get specific audit event        |

---

### Notification Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/notifications            | List all notifications         |
| GET    | /api/v1/notifications/{id}       | Get notification details       |
| PUT    | /api/v1/notifications/config     | Update notification settings   |

--- 

### Analytics Service
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | /api/v1/analytics/dashboard      | Access analytics dashboard     |
| GET    | /api/v1/analytics/reports        | Generate analytical reports    |
| GET    | /api/v1/analytics/events         | Retrieve tracked events        |

---

## Event-Driven Architecture

### Event Publishers
| Service            | Events Published                   | Trigger Condition                          |
|--------------------|------------------------------------|--------------------------------------------|
| Patient            | PatientRegistered, PatientUpdated | Patient record creation/update            |
| Appointment        | AppointmentCreated, Updated, Cancelled | Appointment lifecycle changes         |
| Pharmacy           | PrescriptionFilled, MedicationDispensed | Medication processing events          |
| Inventory          | StockLow, StockReplenished        | Inventory threshold changes               |

---

### Event Consumers
| Service            | Events Consumed                   | Business Logic                             |
|--------------------|------------------------------------|--------------------------------------------|
| Inventory          | MedicationDispensed               | Decrement stock, trigger reorder           |
| Billing & Claims   | AppointmentCreated, PatientRegistered | Initialize billing records              |
| Notification       | Various appointment/billing events | Send user notifications                 |
| Audit Logging      | All domain events                 | Maintain compliance records                |
| Analytics          | All domain events                 | Update business intelligence dashboards    |

