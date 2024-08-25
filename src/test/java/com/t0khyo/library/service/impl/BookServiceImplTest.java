package com.t0khyo.library.service.impl;

import com.t0khyo.library.exception.BookNotFoundException;
import com.t0khyo.library.mapper.BookMapper;
import com.t0khyo.library.model.dto.request.BookRequest;
import com.t0khyo.library.model.dto.response.BookResponse;
import com.t0khyo.library.model.entity.Book;
import com.t0khyo.library.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    // set up
    private final long bookId = 1L;
    @InjectMocks
    private BookServiceImpl bookService;
    // dependencies
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    private Book book;
    private BookResponse bookResponse;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        bookRequest = BookRequest.builder()
                .title("testing title")
                .author("tester")
                .publicationDate(2024)
                .ISBN("978-3-16-148410-0")
                .build();

        bookResponse = BookResponse.builder()
                .id(bookId)
                .title("testing title")
                .author("tester")
                .publicationDate(2024)
                .ISBN("978-3-16-148410-0")
                .borrowingRecords(List.of())
                .build();

        book = Book.builder()
                .id(bookId)
                .title("testing title")
                .author("tester")
                .publicationDate(2024)
                .ISBN("978-3-16-148410-0")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll_ShouldReturnListOfBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookResponse);

        // Act
        List<BookResponse> result = bookService.getAll();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(bookResponse, result.get(0), "The returned book response should match the expected value");

        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void getById_shouldReturnBook() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDetailedDto(book)).thenReturn(bookResponse);

        // Act
        BookResponse result = bookService.getById(bookId);

        // Assert
        assertNotNull(result);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapper, times(1)).toDetailedDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void getById_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getById(bookId);
        });

        // Assert
        assertEquals("Book with id: " + bookId + " not found.", exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void save_shouldSaveBook() {
        // Arrange
        when(bookMapper.toEntity(bookRequest)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookResponse);

        // Act
        BookResponse result = bookService.save(bookRequest);

        // Assert
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(bookRequest.author(), result.author());
        assertEquals(bookRequest.title(), result.title());
        assertEquals(bookRequest.publicationDate(), result.publicationDate());
        assertEquals(bookRequest.ISBN(), result.ISBN());
        assertFalse(result.borrowed(), "Newly saved book should not be true.");

        verify(bookMapper, times(1)).toEntity(bookRequest);
        verify(bookMapper, times(1)).toDto(book);
        verify(bookRepository, times(1)).save(book);

        verifyNoMoreInteractions(bookMapper, bookRepository);
    }

    @Test
    void update() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookMapper).update(bookRequest, book);
        when(bookMapper.toDto(book)).thenReturn(bookResponse);
        // Act
        BookResponse result = bookService.update(bookId, bookRequest);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.id());
        assertEquals(bookRequest.author(), result.author());
        assertEquals(bookRequest.title(), result.title());
        assertEquals(bookRequest.publicationDate(), result.publicationDate());
        assertEquals(bookRequest.ISBN(), result.ISBN());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapper, times(1)).toDto(book);

        verifyNoMoreInteractions(bookMapper, bookRepository);
    }

    @Test
    void deleteById_shouldDeleteBook() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        // Act
        bookService.deleteById(bookId);

        // Assert
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(book);

        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(bookMapper);
    }
}