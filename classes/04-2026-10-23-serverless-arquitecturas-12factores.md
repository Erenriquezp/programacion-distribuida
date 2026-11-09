# 04 · Serverless, arquitecturas y Twelve-Factor (I–IV)

## Serverless
- El desarrollador se centra en la lógica de negocio.
- El proveedor configura y gestiona la infraestructura que la app necesita.
- Basado en eventos: el código se ejecuta solo cuando llega un evento (modelo FaaS, ver [02](02-2026-10-15-cloud-native.md#modelos-de-servicio)).

## Arquitecturas

| Tipo | Característica |
|---|---|
| Monolítica | Un único componente desplegable |
| Multicapa (n-tier) | Modelo cliente-servidor: presentación → lógica de negocio → datos |
| Service-based | Pocos servicios de grano grueso agrupados por área; suelen compartir BD |
| Microservicios | Cada microservicio tiene su propia BD y se encarga de una funcionalidad específica (subdominio) |

## Tipos de aplicaciones

| Tipo | Descripción |
|---|---|
| Cloud-unready | No puede ejecutarse en la nube sin rediseño |
| Cloud-ready | Diseñada para un equipo local o como servicio *on-premise* (in situ), adaptada para correr en la nube |
| Cloud native | Diseñada específicamente para la nube; la unidad desplegable es el contenedor |

## Twelve-Factor App
Metodología publicada por Heroku (2011). Factores V–XII: [05](05-2026-10-26-12factores.md), [06](06-2026-10-29-12factores-arquitecturas.md).

| # | Factor | Regla |
|---|---|---|
| I | **Codebase** | Código bajo control de versiones (Git). Un repositorio por microservicio; el mismo código se despliega en varios ambientes |
| II | **Dependencies** | Declaradas explícitamente (Maven, Gradle); simplifica la configuración del entorno |
| III | **Config** | Separada del código, en variables de entorno; nunca en el repositorio del código. Permite desplegar varias copias del microservicio con distinta configuración |
| IV | **Backing services** | Servicios externos (BD, colas, caché) como recursos adjuntos: debe poder cambiarse uno local por uno gestionado por terceros sin modificar el código |
