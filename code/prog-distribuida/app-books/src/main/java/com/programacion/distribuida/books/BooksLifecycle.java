package com.programacion.distribuida.books;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class BooksLifecycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer appPort;

    @Inject
    @ConfigProperty(name = "app.name", defaultValue = "app-books")
    String appName;

    String ipAddress = "127.0.0.1";
    String serviceId;

    @PostConstruct
    public void construct() {
        serviceId = "%s-%s:%d".formatted(appName, ipAddress, appPort);
    }

    ConsulClient consulClient(Vertx vertx) {
        ConsulClientOptions options = new ConsulClientOptions()
                .setHost(consulHost)
                .setPort(consulPort);
        return ConsulClient.create(vertx, options);
    }

    public void init(@Observes StartupEvent event, Vertx vertx) {
        System.out.println("Books service is starting...");

        ServiceOptions serviceOptions = new ServiceOptions()
                .setName(appName)
                .setId(serviceId)
                .setAddress(ipAddress)
                .setPort(appPort);

        consulClient(vertx).registerService(serviceOptions)
                .onSuccess(v -> System.out.println("Service registered in Consul with ID: " + serviceId))
                .onFailure(err -> System.err.println("Failed to register service in Consul: " + err.getMessage()));
    }

    public void destroy(@Observes ShutdownEvent event, Vertx vertx) {
        System.out.println("Books service is stopping...");

        consulClient(vertx).deregisterService(serviceId)
                .onSuccess(v -> System.out.println("Service unregistered from Consul with ID: " + serviceId))
                .onFailure(err -> System.err.println("Failed to unregister service from Consul: " + err.getMessage()));
    }
}
