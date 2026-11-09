# 02 · Aplicaciones Cloud Native

## Contexto
Sistemas altamente distribuidos que se ejecutan en la nube:
- Varias aplicaciones en distintos nodos que se comunican por red.
- Entornos dinámicos y cambio constante: deben resistir fallos y cambios.

## Modelo Cloud Native

| Elemento | Contenido |
|---|---|
| **The Cloud** (plataforma) | Infraestructura TI: IaaS, PaaS, SaaS… |
| **Topologies** | Contenedores, orquestación, serverless |
| **Application properties** | Scalability, loose coupling, resilience, observability, manageability → ver [03](03-2026-10-16-propiedades-topologias.md) |
| **Goals** | Velocidad de entrega, reducción de costos |

- **Cloud Native**: enfoque para desarrollar aplicaciones que integra *platforms*, *properties* y *practices*.
- **CNCF**: Cloud Native Computing Foundation.
- **NIST**: National Institute of Standards and Technology; define formalmente el cloud computing.

## Elasticidad
Los recursos se aprovisionan y liberan dinámicamente según la demanda.

## Modelos de despliegue

| Modelo | Descripción |
|---|---|
| Private cloud | Usada por una sola organización |
| Public cloud | Ofrecida por un proveedor: AWS, Azure, Google Cloud |
| Hybrid cloud | Combinación de privada y pública |

## Modelos de servicio
De mayor a menor control del cliente:

| Modelo | El cliente… | Ejemplo |
|---|---|---|
| **IaaS** | Controla y aprovisiona los recursos (VM, red, almacenamiento) | AWS EC2 |
| **CaaS** | No controla la infraestructura; aprovisiona y gestiona contenedores | Kubernetes gestionado |
| **PaaS** | Recibe infraestructura, herramientas y una API para construir y desplegar apps | Cloud Foundry |
| **FaaS** | Solo escribe lógica de negocio (funciones); serverless, cobro por tiempo de ejecución | AWS Lambda |
| **SaaS** | Solo accede a la aplicación | Gmail |
