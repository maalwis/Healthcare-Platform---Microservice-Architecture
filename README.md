# Healthcare Microservices System

Modern healthcare management platform built with microservices architecture. Features secure patient data handling, appointment scheduling, pharmacy operations, and real-time analytics with event-driven communication.

## Table of Contents
- [System Architecture](#system-architecture)
  - [API Gateway](#api-gateway)
  - [Authentication & Authorization](#authentication--authorization)
  - [Service Registry](#service-registry)
  - [Resilience Features](#resilience-features)
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
- [Getting Started](#getting-started)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## System Architecture

### API Gateway
- **Central Entry Point**: Handles all incoming requests
- **Security Layer**:
  - JWT validation & token introspection
  - Role-based access control (RBAC)
  - Rate limiting (requests/sec per service)
- **Routing**: Forwards authorized requests to appropriate microservices
- **Integration**: Communicates with AuthenticationService via Feign client

### Authentication & Authorization
- **JWT Management**:
  - Token issuance/validation
  - Token revocation list (RL)
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

[Remaining sections about event architecture, getting started, etc., remain unchanged...]

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

