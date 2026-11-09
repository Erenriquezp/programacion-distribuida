# 03 · Propiedades Cloud Native y topologías

- **Cloud**: *dónde* se ejecutan las aplicaciones.
- **Cloud Native**: *cómo* se construyen y ejecutan.

## Propiedades (CNCF)

| Propiedad | Definición |
|---|---|
| **Scalability** | Escalado horizontal: más instancias detrás de un balanceador de carga. Las instancias son *stateless*; el estado vive en un nodo dedicado (BD, caché). Hay que resolver *dónde* está cada instancia (IP → registro/descubrimiento) |
| **Loose coupling** | Las partes del sistema (módulos, capas, servicios) tienen poco o ningún conocimiento entre sí |
| **Resilience** | El software sigue disponible aunque existan fallos |
| **Observability** | Medida de qué tan bien se infiere el estado interno a partir de las salidas externas: métricas, logs, trazas. Prácticas: monitoring, alerting, tracing, log aggregation |
| **Manageability** | Facilidad con que una entrada externa cambia el estado o comportamiento del sistema; desplegar y actualizar mientras sigue en ejecución |

## Topologías

### Problema
Una app depende de un entorno específico (SO, JDK, etc.); dedicar una máquina por app es ineficiente.

### Virtualización

| Hipervisor | Se ejecuta sobre | Ejemplo |
|---|---|---|
| Tipo 1 (bare metal) | Hardware | VMware ESXi, Hyper-V |
| Tipo 2 (hosted) | Sistema operativo anfitrión | VirtualBox |

La virtualización proporciona la infraestructura (IaaS) sobre la que se ejecutan los contenedores.

### Contenedores
> Paquete que incluye todo lo necesario para ejecutar una aplicación. Comparte el kernel del host que lo ejecuta.

| | Máquina virtual | Contenedor |
|---|---|---|
| Kernel | Propio (SO invitado completo) | Compartido con el host |
| Peso / arranque | Pesada, lenta | Ligero, rápido |

- Una app se divide en módulos; cada uno corre en su contenedor, pudiendo compartir máquina.

### Orquestación
Herramientas: Kubernetes, Docker Swarm, Apache Mesos.
- Gestión del clúster (crear/destruir VM/nodos).
- Programar (*scheduling*) y desplegar contenedores.
- Escalar contenedores dinámicamente.
- Crear redes para que los contenedores se comuniquen.
- Configuración declarativa mediante archivos YAML.
