package com.programacion.distribuida.books.services.impl;

import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import com.programacion.distribuida.books.services.interfaces.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class BookServiceImpl implements BookService {
    final BookRepository bookRepositoryImpl;

    @Inject
    public BookServiceImpl(BookRepository bookRepositoryImpl) {
        this.bookRepositoryImpl = bookRepositoryImpl;
    }

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .price(book.getPrice())
                .inventorySold(book.getInventory() != null ? book.getInventory().getSold() : null)
                .inventorySupplied(book.getInventory() != null ? book.getInventory().getSupplied() : null)
                .build();
    }

    public List<BookDto> findAll() {
        return bookRepositoryImpl.streamAll()
                .map(this::toDto)
                .toList();
    }

    public Optional<BookDto> findByIdOptional(String isbn) {
        return bookRepositoryImpl.findByIdOptional(isbn)
                .map(this::toDto);
    }
}
