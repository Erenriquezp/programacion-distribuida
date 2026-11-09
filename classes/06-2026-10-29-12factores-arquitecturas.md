# 06 · Twelve-Factor (IX–XII) y arquitecturas

Factores anteriores: [04](04-2026-10-23-serverless-arquitecturas-12factores.md#twelve-factor-app), [05](05-2026-10-26-12factores.md).

| # | Factor | Regla |
|---|---|---|
| IX | **Disposability** | La app es desechable: arranca rápido y se detiene limpiamente bajo demanda (elastic scaling) |
| X | **Dev/prod parity** | Minimizar las diferencias entre todos los ambientes (dev, test, prod) |
| XI | **Logs** | La app emite logs como flujo de eventos (stdout) en formato estructurado; herramientas externas los recolectan y gestionan (Logstash, Fluentd) |
| XII | **Admin processes** | Tareas administrativas (migraciones, conversiones) como scripts versionados en el repositorio, ejecutados de forma automatizada |

## Arquitectura tradicional vs. microservicios

**Tradicional (un WAR):**
```
User → View → Controller → Services → Model → DB
```

**Microservicios:** tantas apps como subdominios (p. ej., un servicio "Gestión de categorías").
```
Presentation layer → [ API layer | Data layer ] → DB
                       └──── microservicio ────┘
```
- Cada microservicio contiene componentes de negocio y acceso remoto.
- Tecnologías: CDI (componentes), REST (acceso remoto), JSON (formato de datos).
