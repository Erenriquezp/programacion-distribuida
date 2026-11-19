package com.programacion.distribuida.books.clients;

import com.programacion.distribuida.books.dtos.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "authors")
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthorRestClient {

//    @GET
//    List<AuthorDto> getAuthors() {
//        return authorRepository.listAll();
//    }
//
//    @GET
//    @Path("/{id}")
//    Response getById(@PathParam("id") Integer id) {
//        return authorRepository.findByIdOptional(id)
//                .map(Response::ok)
//                .orElse(Response.status(Response.Status.NOT_FOUND))
//                .build();
//    }

    @GET
    @Path("/find/{isbn}")
    List<AuthorDto> findByBook(@PathParam("isbn") String isbn);
}

