# MicroProfile REST Client (Quarkus)

## Dependencias
```kotlin
implementation("io.quarkus:quarkus-rest-client")
implementation("io.quarkus:quarkus-rest-client-jsonb")
```

## Interfaz
```java
@RegisterRestClient(configKey = "authors")
@Path("/authors")
public interface AuthorRestClient {
    @GET @Path("/find/{isbn}")
    List<AuthorDto> findByBook(@PathParam("isbn") String isbn);
}
```
- `@PathParam` para `{isbn}` en la ruta; `@QueryParam` solo para `?isbn=`.

## Configuración
```properties
quarkus.rest-client.authors.url=http://localhost:8070
```

## Inyección
```java
@Inject @RestClient AuthorRestClient authors;
```

## Config: sobrescribir
`java -Dquarkus.http.port=8070 -jar build/quarkus-app/quarkus-run.jar`

Ordinales: `-D` 400 > env 300 > `application.properties` 250 > `microprofile-config.properties` 100.

## JPA (Panache + PostgreSQL)
```kotlin
implementation("io.quarkus:quarkus-hibernate-orm-panache")
implementation("io.quarkus:quarkus-jdbc-postgresql")
```
```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/books-db
quarkus.datasource.username=postgres
quarkus.datasource.password=postgres
```
