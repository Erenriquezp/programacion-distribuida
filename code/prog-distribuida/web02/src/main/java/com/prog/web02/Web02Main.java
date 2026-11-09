package com.prog.web02;

import jakarta.ws.rs.SeBootstrap;

import java.net.URI;

public class Web02Main {

    static void main() throws Exception{

        SeBootstrap.Configuration config =
                SeBootstrap.
                Configuration.builder().
                host("0.0.0.0").
                port(8080).
                protocol("http").build();

        SeBootstrap.start(MiAplicacionRest.class, config)
                .thenAccept(instance -> {
                    System.out.println(instance);
                    URI uri = instance.configuration().baseUri();
                    System.out.println("Servidor iniciado en " + uri);

                }).exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });
        Thread.currentThread().join();

    }
}
