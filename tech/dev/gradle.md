# Gradle

## Proyecto del curso
- `code/prog-distribuida/`: proyecto raíz (Gradle 9.8.2, Kotlin DSL).
- Módulos incluidos en `settings.gradle.kts`: `web01`, `web02`, `web03`, `app-authors`.
- Un módulo concreto: `./gradlew :web02:installDist`.

## Wrapper
- `gradlew` (Linux) / `gradlew.bat` (Windows): descarga y usa la versión fijada en `gradle/wrapper/gradle-wrapper.properties`.
- No requiere tener Gradle instalado, solo un JDK.

```bash
./gradlew build        # compila, empaqueta (jar/war) y ejecuta tests
./gradlew clean        # borra build/
```

## Plugin `war`
Genera un `.war` en `build/libs/` con `src/main/webapp/` en la raíz del archivo y las clases en `WEB-INF/classes`.

```kotlin
plugins {
    id("java")
    id("war")
}
```

## Toolchain
Fija la versión de Java para compilar, independiente del JDK que ejecuta Gradle:

```kotlin
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
```
- Si no encuentra un JDK 25 instalado, falla, salvo que el plugin `foojay-resolver` esté configurado para descargarlo.

## Dependencias
```kotlin
repositories { mavenCentral() }
dependencies {
    implementation("commons-collections:commons-collections:3.2.2")
}
```
- `implementation`: se compila y se empaqueta (en un WAR va a `WEB-INF/lib`).
- `compileOnly`: solo para compilar; en ejecución la aporta el servidor.

## Plugins de distribución

| Plugin | Tarea | Resultado |
|---|---|---|
| `application` | `installDist` | `build/install/<módulo>/bin` y `lib` |
| `com.gradleup.shadow` | `shadowJar` | `build/libs/<módulo>-all.jar` (fat JAR) |
| `io.quarkus` | `quarkusBuild` | `build/quarkus-app/quarkus-run.jar` con `app/`, `lib/` y `quarkus/` |

## Entregas
- No subir `build/` ni `.gradle/`: son regenerables.
