package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@ToString
public class Inventory {

    @Id
    @OneToOne
    @JoinColumn(name = "book_isbn")
    @JsonbTransient
    @ToString.Exclude
    private Book book;

    private Integer sold;
    private Integer supplied;

}
