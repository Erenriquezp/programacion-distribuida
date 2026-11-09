# Apache Tomcat 11

- Es un contenedor de Servlets/JSP, no un servidor Jakarta EE completo.
- Puerto por defecto: `8080`.

## Instalación
1. Descargar Tomcat 11, distribución Core (`tar.gz` en Linux, `zip` en Windows).
2. Descomprimir, por ejemplo en `~/servidores/apache-tomcat-11.0.27`.

## Usuario del Manager
`conf/tomcat-users.xml`, antes de `</tomcat-users>`:

```xml
<role rolename="manager-gui"/>
<user username="tomcat" password="s3cret" roles="manager-gui"/>
```
- Reiniciar Tomcat después de editarlo.

## Arranque

| Comando (en `bin/`) | Efecto |
|---|---|
| `./catalina.sh start` | Inicia en segundo plano |
| `./catalina.sh run` | Inicia en primer plano, con logs en la terminal (Ctrl+C detiene) |
| `./catalina.sh stop` | Detiene |
| `catalina.bat start/run/stop` | Equivalente en Windows |

## Desplegar un WAR
1. Generar el WAR: `./gradlew build` → `build/libs/web01-1.0-SNAPSHOT.war`.
2. Abrir `http://localhost:8080` → **Manager App** → iniciar sesión.
3. "Archivo WAR a desplegar" → Examinar → Desplegar.
4. Abrir `http://localhost:8080/web01-1.0-SNAPSHOT` (el context path es el nombre del WAR).

Alternativa: copiar el `.war` a `webapps/`, y Tomcat lo despliega automáticamente.

## Despliegue por línea de comandos
Requiere el rol `manager-script` (`roles="manager-gui,manager-script"`):

```bash
curl -u tomcat:s3cret -T build/libs/web01-1.0-SNAPSHOT.war \
  "http://localhost:8080/manager/text/deploy?path=/web01-1.0-SNAPSHOT&update=true"
curl -u tomcat:s3cret http://localhost:8080/manager/text/list
```

## Windows (casa)
- Instalado en `%USERPROFILE%\servidores\apache-tomcat-11.0.27` (zip 11.0.27, igual que en el laboratorio).
- Usuario `tomcat`/`s3cret` con los roles `manager-gui` y `manager-script`.
- `catalina.bat start` abre una ventana nueva con los logs; `catalina.bat run` usa la terminal actual.
