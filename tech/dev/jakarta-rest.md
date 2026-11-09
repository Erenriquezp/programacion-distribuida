# Jakarta REST: despliegue y embebido

## API
```kotlin
dependencies {
    compileOnly("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
}
```
- `compileOnly`: el servidor aporta la implementación; requiere el plugin `war` para desplegar.
- Tomcat no incluye implementación REST; TomEE y GlassFish sí.

## Servidores
- TomEE 10.2.0 Web Profile: <https://tomee.apache.org/download.html>
- GlassFish Web 8.0.5: <https://www.eclipse.org/downloads/download.php?file=/ee4j/glassfish/web-8.0.5.zip>

```bash
cd apache-tomee-webprofile-10.2.0/bin
chmod +x catalina.sh
./catalina.sh run
```

URL: `http://localhost:8080/web01-1.0-SNAPSHOT/api/hola/nino` (context = nombre del WAR, `/api` = `@ApplicationPath`, `/hola/{name}` = `@Path`).

## Embebido con RESTEasy + Undertow
```kotlin
plugins {
    id("java")
    id("application")
}
dependencies {
    implementation("org.jboss.resteasy:resteasy-undertow:7.0.5.Final")
    implementation("org.jboss.resteasy:resteasy-core:7.0.5.Final")
}
```

```java
SeBootstrap.Configuration config = SeBootstrap.Configuration.builder()
        .host("0.0.0.0").port(8080).protocol("http").build();
SeBootstrap.start(MiAplicacionRest.class, config);
Thread.currentThread().join();
```

```bash
./gradlew :web02:installDist
cd web02/build/install/web02/bin
./web02 com.prog.web02.Web02Main      # Windows: web02.bat com.prog.web02.Web02Main
curl http://localhost:8080/api/hola/nino
```
- Sin `application { mainClass = ... }`, la clase principal se pasa como argumento.
