# 09 · Helidon SE: servidor embebido, JSON y acceso a datos

Proyecto: [`web03`](../code/prog-distribuida/web03), paquete `com.progra.web03`. Sigue el modelo "llevar el servidor a la app" ([nota 08](08-2026-11-05-jakarta-rest-servidores.md)).

## Dependencias (Helidon 4.5.6)

| Área | Artefacto |
|---|---|
| Servidor | `helidon-webserver` |
| JSON | `helidon-http-media-jsonb`, `helidon-http-media-jsonp` |
| Configuración | `helidon-config-yaml` |
| Datos | `helidon-dbclient`, `helidon-dbclient-jdbc`, `helidon-dbclient-hikari`, `postgresql` |

Plugins: `io.freefair.lombok` (`@Builder`, `@Data` en `Book`) y `com.gradleup.shadow`.

## Fat JAR
- `shadowJar` empaqueta la app y sus dependencias en `build/libs/web03-all.jar`.
- `mergeServiceFiles()` combina los `META-INF/services`.
- `Main-Class` en el manifiesto permite ejecutar:

```bash
cd web03/build/libs
java -jar web03-all.jar
```

## Servidor y rutas
- `Config.create()` lee `application.yaml`; `config.get("server")` y `config.get("db")` obtienen cada sección.
- `WebServer.builder()`: `.config(...)`, `.mediaContext(...)` (registra JSON-B) y `.routing(it -> it.get("/books", ...))`.
- Cada petición admite **un solo** `res.send(...)`.

| API | Uso | En clase |
|---|---|---|
| JSON-P | Construir el JSON a mano (`JsonBuilderFactory`) | No |
| JSON-B | Serializar objetos Java (`res.send(books)`) | Sí |

## Acceso a datos
- `DbClient.builder().config(configdb).build()` crea el cliente con pool Hikari.
- `dbClient.execute().createQuery("select * from books").execute().map(...).toList()`.
- `web03/docker-compose.yml`: Postgres (`5432`, BD `web03-db`) y Adminer (`9090`).
- `handleBooks` lee las columnas `title` e `isbn` de la tabla `books`.

## Error de configuración
`NullPointerException` en `JdbcClient` (`connectionPool()` es `null`): `db` estaba anidado bajo `server`, por lo que `config.get("db")` no encontraba la sección. Debe estar en la raíz del YAML.

La URL JDBC correcta es `jdbc:postgresql://localhost:5432/web03-db`.
