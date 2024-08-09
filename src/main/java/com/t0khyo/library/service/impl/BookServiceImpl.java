package com.t0khyo.library.service.impl;

import com.t0khyo.library.exception.BookNotFoundException;
import com.t0khyo.library.mapper.BookMapper;
import com.t0khyo.library.model.dto.request.BookRequest;
import com.t0khyo.library.model.dto.response.BookResponse;
import com.t0khyo.library.model.entity.Book;
import com.t0khyo.library.repository.BookRepository;
import com.t0khyo.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookResponse> getAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = findBookById(id);
        return bookMapper.toDto(book);
    }

    @Override
    public BookResponse save(BookRequest bookRequest) {
        Book book = bookMapper.toEntity(bookRequest);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Transactional
    @Override
    public BookResponse update(Long id, BookRequest bookRequest) {
        Book existingBook = findBookById(id);
        bookMapper.update(bookRequest, existingBook);
        return bookMapper.toDto(existingBook);
    }

    @Override
    public void deleteById(Long id) {
        Book book = findBookById(id);
        bookRepository.delete(book);
    }

    // --- private methods ---
    private Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with Id: " + id + " not found."));
    }
}
