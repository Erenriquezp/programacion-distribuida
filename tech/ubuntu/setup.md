# Ubuntu: entorno del laboratorio

## Java
```bash
sudo apt update
sudo apt install openjdk-25-jre-headless   # visto en clase: solo ejecuta
sudo apt install openjdk-25-jdk            # necesario para compilar (javac)
java -version
```

## Docker
- Instalación vía snap (paquete `docker`, también disponible en el Centro de Aplicaciones de Ubuntu).
- **Dockhand**: interfaz web self-hosted para gestionar Docker (repositorio en GitHub).

```bash
sudo snap install docker
docker pull nginx
docker pull postgres
docker image ls
```

## Tomcat
- Ubicación en el laboratorio: `~/servidores/apache-tomcat-11.0.27`.
- Arranque: `cd bin` → `./catalina.sh run` (primer plano) o `./catalina.sh start` (segundo plano).
- Usuario del Manager en `conf/tomcat-users.xml`:
  ```xml
  <role rolename="manager-gui"/>
  <user username="tomcat" password="s3cret" roles="manager-gui"/>
  ```
- Despliegue: `http://localhost:8080` → Manager App → "Archivo WAR a desplegar".
