# 07 · Servidores de aplicaciones, MicroProfile y despliegue WAR

## Perfiles Jakarta EE
Especificaciones: <https://jakarta.ee/specifications>

| Perfil | Alcance | Ejemplo |
|---|---|---|
| Platform (Full) | Todas las especificaciones | Payara Server, WildFly |
| Web Profile | Subconjunto para apps web | TomEE |
| Core Profile | Mínimo para microservicios (CDI Lite, REST, JSON) | Quarkus |

- **Servidor de aplicaciones tradicional**: implementa el perfil completo; se le despliegan apps.
- **JeAS** (*Just enough Application Server*): solo las especificaciones que la app necesita.
- **Tomcat** no es servidor Jakarta EE completo: es un contenedor de Servlets/JSP.

## MicroProfile
Especificación para construir microservicios usando Jakarta EE (Config, Health, Metrics, Fault Tolerance, REST Client…).

## Docker en Linux
- Instalación vía snap.
- Dockhand: interfaz web self-hosted para gestionar Docker (GitHub).

```bash
sudo apt update
sudo apt install openjdk-25-jre-headless
docker pull nginx
docker pull postgres
docker image ls
```

## Despliegue WAR en Tomcat 11
Proyecto: [`code/prog-distribuida/web01`](../code/prog-distribuida/web01). Gradle genera el `.war` con el plugin `war`.

1. Descargar Apache Tomcat 11 (Core, `tar.gz`) y descomprimir.
2. Habilitar el Manager en `conf/tomcat-users.xml`:
   ```xml
   <role rolename="manager-gui"/>
   <user username="tomcat" password="s3cret" roles="manager-gui"/>
   ```
3. Iniciar: `cd bin` → `./catalina.sh start` (segundo plano) o `./catalina.sh run` (primer plano).
4. Abrir `http://localhost:8080` → **Manager App** → iniciar sesión.
5. "Archivo WAR a desplegar" → seleccionar el `.war` → Desplegar.
6. Probar en `http://localhost:8080/web01-1.0-SNAPSHOT`.
