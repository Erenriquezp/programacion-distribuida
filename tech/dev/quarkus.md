# Quarkus 3.40.1.1

Generador: <https://code.quarkus.io/?b=GRADLE_KOTLIN_DSL&e=rest&extension-search=rest>

## Gradle
```kotlin
plugins {
    id("java")
    id("io.quarkus") version "3.40.1.1"
    id("io.freefair.lombok") version "9.8.3"
}

repositories { mavenCentral() }

val quarkusVersion = "3.40.1.1"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))
    implementation("io.quarkus:quarkus-arc")          // CDI
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
```

## Comandos
```bash
./gradlew :app-authors:quarkusDev     # desarrollo: live coding, Dev UI, debug en 5005
./gradlew :app-authors:quarkusBuild   # empaqueta en build/quarkus-app/

cd app-authors/build/quarkus-app
java -jar quarkus-run.jar             # perfil prod, puerto 8080
```

- Con Quarkus CLI: `quarkus dev`.
- `quarkus-app/` se distribuye completa: `quarkus-run.jar` necesita `app/`, `lib/` y `quarkus/`.
- Dev UI (solo en dev): `http://localhost:8080/q/dev-ui/extensions`.
- Prueba: `curl http://localhost:8080/config`.

## REST
```java
@Path("/config")
public class TestConfigRest {
    @GET
    public String test() { return "Hello World!"; }
}
```
- No requiere `@ApplicationPath` ni una clase `Application`.

## Configuración
- `src/main/resources/application.properties`: Quarkus.
- `src/main/resources/META-INF/microprofile-config.properties`: MicroProfile Config.

## Errores

| Síntoma | Causa |
|---|---|
| `no se ha encontrado o cargado la clase principal jar` | Falta el guion: `java -jar` |
| `Unable to access jarfile quarkus-run.jar` | El directorio actual debe ser `build/quarkus-app/` |
| `META-INF/mircroprofile-config.properties` ignorado | Nombre mal escrito: `microprofile-config.properties` |
