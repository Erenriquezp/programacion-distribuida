# 13 · Service Discovery con Consul

Proyectos: [`app-authors`](../code/prog-distribuida/app-authors) (8070) y [`app-books`](../code/prog-distribuida/app-books) (8080). REST Client y Config: [nota 11](11-2026-11-16-rest-client.md).

## Problema
- Con URL fija (`http://localhost:8070`) el cliente se rompe si el servicio cambia de host/puerto o escala a varias instancias.
- **Service Discovery**: los servicios se registran en un **registro** y los clientes consultan ahí la ubicación real.

| Concepto | Detalle |
|---|---|
| Service registry | Base de datos de instancias disponibles (Consul, Eureka, etcd) |
| Self-registration | El servicio se registra al iniciar y se da de baja al detenerse |
| Third-party registration | Otro componente registra (p. ej. Kubernetes) |
| Client-side discovery | El cliente consulta el registro y elige instancia (balanceo en el cliente) |
| Server-side discovery | Un router/gateway consulta el registro por el cliente |
| Health check | El registro descarta instancias caídas |

## Consul (HashiCorp)
- Registro de servicios, health checks, key/value y UI web.
- Modo desarrollo: `./consul agent -dev` (un nodo, en memoria, UI habilitada).
- UI: `http://localhost:8500/ui` → Services / Nodes → *Service instances*.

| Puerto | Uso |
|---|---|
| 8500 | HTTP API y UI |
| 8502 | gRPC |
| 8600 | DNS |
| 8300 / 8301 / 8302 | RPC servidor / gossip LAN / WAN |

## Registro en `app-authors`
- Dependencia: `io.smallrye.reactive:smallrye-mutiny-vertx-consul-client` (cliente Vert.x de Consul).
- `AuthorsLifecycle` (`@ApplicationScoped`) observa eventos de ciclo de vida de Quarkus:

| Evento | Acción |
|---|---|
| `@Observes StartupEvent` | `client.registerService(ServiceOptions)` |
| `@Observes ShutdownEvent` | `client.deregisterService(serviceId)` |

- `ServiceOptions`: `name` (lógico, `app-authors`), `id` único por instancia, `address`, `port`.
- `serviceId = "%s-%s:%d"` → `app-authors-127.0.0.1:8070`: el puerto en el id permite varias instancias del mismo servicio.
- `@PostConstruct` arma el id una vez inyectada la configuración.
- Config externalizada (`microprofile-config.properties`): `app.name`, `consul.host`, `consul.port`; `app.title` queda como nombre descriptivo.
- Sin health check registrado: Consul muestra *No service checks*.

## Descubrimiento en `app-books`
- Se registra igual (`BooksLifecycle`, nombre `app-books`).
- **SmallRye Stork** (`quarkus-smallrye-stork` + `stork-service-discovery-consul`): resuelve `stork://app-authors` consultando Consul.
- El REST Client usa `stork://app-authors` en lugar de la URL fija; Stork elige instancia con **round-robin** (client-side discovery).
- Varias instancias de authors: `java -Dquarkus.http.port=8071 -jar build/quarkus-app/quarkus-run.jar`.
