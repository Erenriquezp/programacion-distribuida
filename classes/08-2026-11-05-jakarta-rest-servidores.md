# 08 · Jakarta REST: servidor de aplicaciones y servidor embebido

## Modelos de despliegue

| Modelo | Idea | Módulo |
|---|---|---|
| Aplicación tradicional | Llevar la app al servidor | `web01` (WAR en TomEE) |
| Distribuida | Llevar el servidor a la app | `web02` (RESTEasy + Undertow) |

## Jakarta RESTful Web Services
Estándar mínimo para exponer recursos REST: `jakarta.ws.rs:jakarta.ws.rs-api:4.0.0`.
- La dependencia es solo la **API**: en ejecución hace falta una implementación.
- Tomcat no la incluye ([nota 07](07-2026-10-30-jakarta-ee-tomcat.md)); un servidor con Web Profile sí.

| Anotación / clase | Función |
|---|---|
| `@ApplicationPath("/api")` + `extends Application` | Prefijo de la app REST |
| `@Path("/hola")`, `@Path("/{name}")` | Ruta del recurso y del método |
| `@GET`, `@Produces("text/plain")` | Verbo HTTP y tipo de respuesta |
| `@PathParam("name")` | Parámetro de la ruta |

## `web01`: WAR en servidor de aplicaciones
- Proyecto: [`web01`](../code/prog-distribuida/web01), paquete `com.prog.web01`.
- Cambiar la dependencia a `compileOnly`: el servidor aporta la implementación y la API no va en el WAR.
- Servidores con implementación: Apache TomEE 10.2.0 Web Profile, GlassFish Web 8.0.5.

```bash
chmod +x catalina.sh
./catalina.sh run
```

Prueba: `http://localhost:8080/web01-1.0-SNAPSHOT/api/hola/nino`

## `web02`: servidor embebido
- Proyecto: [`web02`](../code/prog-distribuida/web02). Dependencias: `resteasy-core` y `resteasy-undertow` 7.0.5.Final.
- `SeBootstrap` (Java SE Bootstrap de Jakarta REST) arranca la app con host, puerto y protocolo desde un `main`.
- `MiAplicacionRest.getClasses()` registra los recursos explícitamente.
- `Thread.currentThread().join()` mantiene vivo el proceso.
- Plugin `application` → Gradle: `web02 > Tasks > distribution > installDist`.

```bash
cd web02/build/install/web02/bin
./web02 com.prog.web02.Web02Main
```

- La clase principal se pasa como argumento porque `build.gradle.kts` no define `mainClass`.
