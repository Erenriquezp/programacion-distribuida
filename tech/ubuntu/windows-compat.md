# Ubuntu ↔ Windows

El bytecode Java y Gradle son multiplataforma. Los problemas vienen de los scripts, el entorno y las versiones.

| Riesgo | Causa | Solución |
|---|---|---|
| `gradlew` no ejecuta en Ubuntu (`Permission denied`) | Se pierde el bit de ejecución al pasar por Windows o por un zip | `chmod +x gradlew` o `sh gradlew build` |
| `bad interpreter: /bin/sh^M` | Saltos de línea CRLF de Windows | `.gitattributes`: `gradlew text eol=lf`; o `sed -i 's/\r$//' gradlew` |
| Versión de Java distinta | El laboratorio usa `openjdk-27` en IntelliJ y en casa hay JDK 25 | Toolchain 25 en `build.gradle.kts` |
| SDK inválido en IntelliJ | `.idea/misc.xml` guarda el nombre del JDK de otra máquina | Project Structure → SDK → JDK 25 (no afecta a Gradle) |
| Rutas absolutas | `/home/labcom/...` no existe en Windows | Rutas relativas o variables de entorno |
| Mayúsculas en nombres | Linux distingue mayúsculas; Windows no | Escribir los nombres exactos |
| Scripts del sistema | `.sh`, `apt` y `snap` solo existen en Linux | Windows: `catalina.bat`, `winget`, Docker Desktop con WSL2 |
| Separador de rutas | `/` en Linux, `\` en Windows | En Java usar `Path.of(...)`; `/` funciona en ambos |
