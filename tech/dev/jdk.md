# JDK 25

## Versión del curso
- **JDK 25 (LTS)**.
- Windows (casa): Temurin 25.0.5 en `C:\Program Files\Eclipse Adoptium\jdk-25.0.5.8-hotspot`, con `JAVA_HOME` de usuario configurado.
- Ubuntu (laboratorio): `sudo apt install openjdk-25-jdk`. En el laboratorio, IntelliJ tiene configurado `openjdk-27`.

```powershell
winget install EclipseAdoptium.Temurin.25.JDK
```

## JRE vs JDK

| | Contiene | Sirve para |
|---|---|---|
| JRE | JVM + bibliotecas | Ejecutar |
| JDK | JRE + `javac`, `jar`, herramientas | Compilar y ejecutar |

## Java 25: main simplificado (JEP 512)
- `main` puede ser de instancia, sin `public` ni `String[] args`.
- `java.lang.IO` permite imprimir sin `System.out`.

```java
public class Main {
    static void main() {
        IO.println("Hello");
    }
}
```
- Este código no compila con JDK menores a 25.
