package com.programacion.distribuida.authors.db;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Table(name = "books_authors")
@Getter
@Setter
@EqualsAndHashCode
public class BookAuthorId {

    @Column(name = "authors_id")
    private int authorsId;

    @Column(name = "books_isbn")
    private String booksIsbn;
}
