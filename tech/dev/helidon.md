# Helidon SE 4.5.6 + Postgres

## Gradle
```kotlin
plugins {
    id("java")
    id("io.freefair.lombok") version "9.8.3"
    id("com.gradleup.shadow") version "9.6.1"
}
java { toolchain { languageVersion = JavaLanguageVersion.of(25) } }
tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE   // si no, se pierden entradas de META-INF/services
    mergeServiceFiles()
}
tasks.jar {
    manifest { attributes["Main-Class"] = "com.progra.web03.Web03Main" }
}
```

```bash
./gradlew :web03:shadowJar
java -jar web03/build/libs/web03-all.jar
```

## `application.yaml`
`server` y `db` son secciones hermanas en la raíz:

```yaml
server:
  port: 8080
  host: "0.0.0.0"
db:
  source: jdbc
  connection:
    url: "jdbc:postgresql://localhost:5432/web03-db"
    username: "postgres"
    password: "postgres"
    poolName: pool-1
```

## Base de datos
```yaml
services:
  web03:
    image: postgres
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: web03-db
    ports:
      - "5432:5432"
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql:ro
  adminer:
    image: adminer
    restart: always
    ports:
      - 9090:8080
```

```bash
docker compose up -d     # en web03/
```
- Adminer: `http://localhost:9090`.
- `init.sql` crea y carga `books` (`isbn`, `title`) solo con el volumen vacío; para recrearla: `docker compose down -v`.
- Prueba: `curl http://localhost:8080/books`.

## Errores

| Síntoma | Causa |
|---|---|
| NPE `JdbcConnectionPool.dbType()` | Falta la sección `db` en la raíz del YAML |
| `Connection refused` en Hikari al iniciar | Postgres no está levantado |
| URL `jdbc:postgres://` | El prefijo correcto es `jdbc:postgresql://` |
| Varios `res.send(...)` en un handler | Solo se admite una respuesta por petición |
