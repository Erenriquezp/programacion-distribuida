# Consul y Service Discovery (Quarkus)

## Consul en modo dev
- Descargar el binario (zip) desde HashiCorp y descomprimir.
- Ubuntu: `./consul agent -dev` · Windows: `consul.exe agent -dev`
- Docker: `docker run -d -p 8500:8500 --name consul hashicorp/consul agent -dev -client=0.0.0.0`
- UI: `http://localhost:8500/ui`

| Puerto | Uso |
|---|---|
| 8500 | HTTP API y UI |
| 8502 | gRPC |
| 8600 | DNS |
| 8300 / 8301 / 8302 | RPC servidor / gossip LAN / WAN |

API útil:
```bash
curl http://localhost:8500/v1/agent/services
curl http://localhost:8500/v1/catalog/service/app-authors
```

## Registro (self-registration) con Vert.x
```kotlin
implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")
```
```properties
app.name=app-authors
consul.host=127.0.0.1
consul.port=8500
```
```java
@ApplicationScoped
public class AuthorsLifecycle {
    @ConfigProperty(name = "consul.host") String consulHost;
    @ConfigProperty(name = "consul.port") Integer consulPort;
    @ConfigProperty(name = "quarkus.http.port") Integer appPort;
    String serviceId; // app-authors-127.0.0.1:8070

    public void init(@Observes StartupEvent event, Vertx vertx) {
        ConsulClient client = ConsulClient.create(vertx,
                new ConsulClientOptions().setHost(consulHost).setPort(consulPort));
        client.registerService(new ServiceOptions()
                .setName("app-authors").setId(serviceId)
                .setAddress("127.0.0.1").setPort(appPort));
    }

    public void destroy(@Observes ShutdownEvent event, Vertx vertx) {
        // ConsulClient.create(...).deregisterService(serviceId)
    }
}
```
- El id debe ser único por instancia (incluye host:puerto).

## Descubrimiento con SmallRye Stork
```kotlin
implementation("io.quarkus:quarkus-smallrye-stork")
implementation("io.smallrye.stork:stork-service-discovery-consul")
implementation("io.quarkus:quarkus-jackson") // requerido por el cliente Consul de Stork
```
```properties
AuthorRestClient/mp-rest/url=stork://app-authors
quarkus.stork.app-authors.service-discovery.type=consul
quarkus.stork.app-authors.service-discovery.consul-host=127.0.0.1
quarkus.stork.app-authors.service-discovery.consul-port=8500
quarkus.stork.app-authors.service-discovery.refresh-period=5s
quarkus.stork.app-authors.load-balancer.type=round-robin
```
- `stork://<nombre>` también funciona con `RestClientBuilder.baseUri(...)`.
- Sin `quarkus-jackson` el build falla (`checkThatJacksonExtensionIsUsedWhenConsulIsOnTheClasspath`).

## Varias instancias
```bash
java -Dquarkus.http.port=8070 -jar build/quarkus-app/quarkus-run.jar
java -Dquarkus.http.port=8071 -jar build/quarkus-app/quarkus-run.jar
```
