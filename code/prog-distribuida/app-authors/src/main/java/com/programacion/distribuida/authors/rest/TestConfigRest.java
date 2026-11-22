package com.programacion.distribuida.authors.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@Path("/config")
@ApplicationScoped
public class TestConfigRest {

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer port;

    // En quarkus podemos ommitir el inject
    @ConfigProperty(name = "app.name")
    String diAppName;

    @GET
    public String test() {
        // Reaizar busqueda sobre las fuentes de configuracion
        Config config = ConfigProvider.getConfig();

        // Listar las fuetnes de configuracion
        config.getConfigSources().forEach(it -> {
            System.out.printf("[%d] %s\n", it.getOrdinal(), it.getName());
        });

        // Recuparar un valor de configuracion
        String appName = config.getValue("app.name", String.class);
        Optional<String> appTitle = config.getOptionalValue("app.title", String.class);

        System.out.println("---------------------------------------------");
        System.out.println("app.name = " + appName);
        System.out.println("port = " + port);
        System.out.println("diAppName = " + diAppName);

        if (appTitle.isPresent()) {
            System.out.println("app.title = " + appTitle.get());
        } else {
            System.out.println("app.title is null");
        }

        // Sobreescribir el puerto o configuraciones -D
        // java -Dquarkus.http.port=8070 -jar build/quarkus-app/quarkus-run.jar
        return "Hello World!";
    }
}