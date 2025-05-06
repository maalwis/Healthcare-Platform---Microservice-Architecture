# Cross‑Cutting Concerns

## 1. Security & Authentication

| Issue / Concern                                                              | Implemented? | Description                                                                                                                                                                                                                                                         | Next Steps                                                                                      |
| ---------------------------------------------------------------------------- | :----------: | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| **JWT‑based auth & introspection**                                           |     ✅ Yes    | All incoming requests (gateway + services) carry a JWT. A custom `AuthenticationGlobalFilter` in the API Gateway (via WebClient) and an `AuthTokenFilter` in downstream services (via OpenFeign) call the AuthenticationService to validate tokens on each request. | • Cache introspection responses in Redis to reduce load                                         |
| • Move JWT signing keys and other secrets into a vault (HashiCorp/AWS/Azure) |              |                                                                                                                                                                                                                                                                     |                                                                                                 |
| **Plaintext secrets in configs**                                             |     ❌ No     | Secrets like JWT signing keys and database credentials are stored unencrypted in each service’s `application.properties`, posing a security risk if repos or images are compromised.                                                                                | • Integrate Spring Cloud Vault or cloud‑provider secret store; load sensitive values at runtime |
| **No mutual service‑to‑service authentication**                              |     ❌ No     | Services communicate over plain HTTP or TLS without client certificates; any pod with network access can call any endpoint.                                                                                                                                         | • Enforce mTLS via a service mesh (Istio/Linkerd) or Spring Cloud’s MTLS support                |

---

## 2. API Gateway & Rate Limiting

| Issue / Concern                                              | Implemented? | Description                                                                                                                                                                         | Next Steps                                                                      |
| ------------------------------------------------------------ | :----------: | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------- |
| **Routing & in‑memory rate limiting**                        |  ✅ Partially | Spring Cloud Gateway handles routing. Rate limiting uses a `ConcurrentMap<String,Bucket>` in‑memory, keyed by JWT user or IP—but it is single‑node and not shared across instances. | • Switch to Bucket4j + Redis adapter for a distributed token‑bucket store       |
| • Externalize per‑user/IP quotas into Config Server or Redis |              |                                                                                                                                                                                     |                                                                                 |
| **Authorization via JWT authorities**                        |     ✅ Yes    | Gateway filters routes based on user roles and scopes extracted from JWT claims, enforcing coarse‑grained RBAC at entry point.                                                      | • Harden attribute mapping; add fine‑grained scope checks on specific endpoints |

---

## 3. Resilience & Fault‑Tolerance

| Issue / Concern                      | Implemented? | Description                                                                                                                                                               | Next Steps                                                                                                                         |
| ------------------------------------ | :----------: | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| **Circuit breaking & retries**       |     ✅ Yes    | Downstream WebClient/Feign calls currently have no fallbacks, retry policies, or timeouts—the checks are configured but lack concrete fallback handlers or backoff logic. | • Fully implement Resilience4j: configure circuit breakers, exponential backoff retries, timeouts, and meaningful fallback methods |
| **Bulkhead / thread‑pool isolation** |     ❌ No     | All asynchronous calls share the same thread pool. A slow or stuck call can exhaust threads and block other flows.                                                        | • Use Resilience4j bulkheads to isolate each external dependency into dedicated thread pools                                       |

---

## 4. Configuration Management

| Issue / Concern               | Implemented? | Description                                                                                                     | Next Steps                                                                     |
| ----------------------------- | :----------: | --------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------ |
| **Static local configs**      |     ❌ No     | Environment overrides (dev/staging/prod) are hard‑coded or manual; updates require a full redeploy per service. | • Centralize configuration using Spring Cloud Config (Git‑backed) or Consul KV |
| **No dynamic config refresh** |     ❌ No     | Sensitive values (DB URLs, API keys) can’t be rotated without restarting the service.                           | • Annotate beans with `@RefreshScope` and enable `/actuator/refresh` endpoint  |

---

## 5. CI/CD Pipeline

Per‑service branches and basic GitHub Actions that build and push Docker images.

| Issue / Concern                        | Implemented? | Description                                                                                                       | Next Steps                                                                                                                            |
| -------------------------------------- | :----------: | ----------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Build & Docker publish**             |     ✅ Yes    | Each branch’s workflow compiles the Spring Boot JAR, builds a multi‑stage Docker image, and pushes to Docker Hub. | • Add static analysis (Checkstyle, SpotBugs), unit/integration tests, and Pact contract tests to the pipeline                         |
| **Security scanning & artifact reuse** |     ❌ No     | No vulnerability scans on images; CI stages rebuild artifacts instead of reusing.                                 | • Integrate Trivy/Snyk for container scanning; share build artifacts across GitHub Actions jobs                                       |
| **Deployment automation**              |     ❌ No     | No automated deploy to Kubernetes or staging clusters; releases remain manual.                                    | • Store Helm charts or Kustomize manifests in an infra repo; add deploy jobs with `helm upgrade` or `kubectl apply` behind approvals. |

---

## 6. Observability (Logging, Metrics, Tracing)

| Issue / Concern         | Implemented? | Description                                                                                       | Next Steps                                                                                                            |
| ----------------------- | :----------: | ------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| **Centralized logging** |     ❌ No     | Apps log to console or flat files without structured JSON; logs are not aggregated or searchable. | • Emit structured JSON to stdout; deploy ELK/EFK or Loki + Grafana for log aggregation and analysis                   |
| **Metrics scraping**    |     ❌ No     | No Prometheus endpoint; service metrics (latencies, error rates) are not exposed or monitored.    | • Add Micrometer and expose `/actuator/prometheus`; configure Prometheus scrape jobs and build Grafana dashboards     |
| **Distributed tracing** |     ❌ No     | No trace context propagation; cannot follow a request end‑to‑end across services.                 | • Integrate OpenTelemetry or Spring Cloud Sleuth + Jaeger/Zipkin; ensure gateway and services propagate trace headers |
| **Alerting & SLOs**     |     ❌ No     | No automated alerts for SLA violations or error spikes.                                           | • Define SLOs (e.g., 99.9% requests <200 ms), configure Prometheus Alertmanager to notify via Slack/PagerDuty         |

---

## 7. Messaging & Eventing

| Issue / Concern             | Implemented? | Description                                                                                | Next Steps                                                                                                       |
| --------------------------- | :----------: | ------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------- |
| **Schema versioning**       |     ❌ No     | Events are raw JSON, no schema registry, risking breaking changes for consumers.           | • Adopt a schema registry (Confluent/Apicurio) and publish Avro/JSON schemas for all events.                     |
| **Retry & DLQ**             |     ❌ No     | No dead‑letter handling; poison messages can loop indefinitely.                            | • Configure per‑queue DLQs with exponential backoff; route failed messages to a “parking lot” for manual review. |
| **TLS & AuthN on RabbitMQ** |     ❌ No     | Broker communicates without strict TLS certs or fine‑grained auth; any client can publish. | • Enable TLS for client connections; require certs or credentials; restrict vhost permissions per service.       |

---

## 8. Data Management & Transactions

| Issue / Concern                      | Implemented? | Description                                                                                         | Next Steps                                                                                                              |
| ------------------------------------ | :----------: | --------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------- |
| **Distributed transactions / sagas** |     ❌ No     | Multi‑service workflows (e.g. appointment→billing) lack orchestration or compensating transactions. | • Implement the Saga pattern via orchestration (e.g. Camunda/Zeebe) or choreography with explicit compensating actions. |
| **Read model / CQRS**                |     ❌ No     | Search and analytics queries hit OLTP databases directly—risking performance degradation.           | • Introduce a read‑optimized store (Elasticsearch, Redis) fed by events for patient search and analytics.               |

---

## 9. Testing Strategy

| Issue / Concern                           | Implemented? | Description                                                                                           | Next Steps                                                                                                                     |
| ----------------------------------------- | :----------: | ----------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| **Consumer‑driven contract tests (Pact)** |     ❌ No     | No contract tests; changes to service APIs can break consumers without warning.                       | • Integrate Pact or Spring Cloud Contract; run provider and consumer verification in CI.                                       |
| **End‑to‑end workflow tests**             |     ❌ No     | Critical user journeys (e.g. patient→appointment→billing) not validated in an integrated environment. | • Automate e2e tests using REST Assured or Cypress against a staging cluster in CI.                                            |
| **Chaos‑engineering & resilience drills** |     ❌ No     | No failure injection to validate circuit breakers and fallbacks under stress.                         | • Use Chaos Monkey for Spring Boot or LitmusChaos to randomly kill pods or inject latency; verify system resilience behaviors. |

---

## 10. Operational Excellence

| Issue / Concern                      | Implemented? | Description                                                                                      | Next Steps                                                                                                                          |
| ------------------------------------ | :----------: | ------------------------------------------------------------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------- |
| **Health checks & readiness probes** |     ❌ No     | No `/actuator/health` or custom probes; pods continue serving traffic even if dependencies fail. | • Expose Spring Boot health indicators (DB, RabbitMQ, Redis) and configure Kubernetes liveness/readiness probes.                    |
| **Autoscaling & resource limits**    |     ❌ No     | No HPAs or resource quotas—services can starve or overconsume cluster resources.                 | • Define CPU/memory requests & limits; enable Horizontal Pod Autoscalers based on CPU or custom metrics (e.g. queue depth).         |
| **Runbooks & on‑call procedures**    |     ❌ No     | No documented recovery steps or runbooks for common failure scenarios.                           | • Write runbooks covering database outages, broker failures, and CI/CD rollback procedures; store in team wiki or runbook platform. |
