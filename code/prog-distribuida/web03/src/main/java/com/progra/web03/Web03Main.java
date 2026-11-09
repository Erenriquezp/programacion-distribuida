package com.progra.web03;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.http.media.jsonb.JsonbSupport;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

public class Web03Main {
    private static DbClient dbClient;

    public static void handleBooks(ServerRequest req, ServerResponse res) {
        var books = dbClient.execute()
                .createQuery("select * from books")
                .execute()
                .map(row -> Book.builder()
                        .title(row.column("title").getString())
                        .isbn(row.column("isbn").getString())
                        .build()
                )
                .toList();
        res.send(books);
    }

    static void main() {
        Config config = Config.create();

        var confighttp = config.get("server");
        var configdb = config.get("db");

        dbClient = DbClient.builder()
                .config(configdb)
                .build();

        WebServer.builder()
                //.port(8080)
                .config(confighttp)
                .mediaContext(ctx -> ctx.addMediaSupport(JsonbSupport.create()))
                .routing(it -> it.get("/books", Web03Main::handleBooks)
                        )
                .build()
                .start();

    }
}
