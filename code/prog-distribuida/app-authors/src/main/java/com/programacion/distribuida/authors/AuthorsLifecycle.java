package com.programacion.distribuida.authors;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.Service;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthorsLifecycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8070")
    Integer appPort;

    @Inject
    @ConfigProperty(name = "app.name", defaultValue = "app-authors")
    String appName;

    String serviceId;

    @PostConstruct
    public void construct() {
        String ipAddress = "127.0.0.1";
        serviceId = "%s-%s:%d".formatted(appName, ipAddress, appPort);
    }

    public void init(@Observes StartupEvent event, Vertx vertx) {
        System.out.println("Authors service is starting...");

        try {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            String ipAddress = "127.0.0.1";

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-authors")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort);

            client.registerService(serviceOptions)
                    .onSuccess(v -> System.out.println("Service registered in Consul with ID: " + serviceId))
                    .onFailure(err -> System.err.println("Failed to register service in Consul: " + err.getMessage()));

        } catch (Exception e) {
            System.err.println("Error registering service in Consul: " + e.getMessage());
        }
    }

    public void destroy(@Observes ShutdownEvent event, Vertx vertx) {
        System.out.println("Authors service is stopping...");

        try {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            client.deregisterService(serviceId)
                    .onSuccess(v -> System.out.println("Service unregistered from Consul with ID: " + serviceId))
                    .onFailure(err -> System.err.println("Failed to unregister service from Consul: " + err.getMessage()));

        } catch (Exception e) {
            System.err.println("Error unregistering service from Consul: " + e.getMessage());
        }
    }
}
