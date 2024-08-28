package com.t0khyo.library.service.impl;

import com.t0khyo.library.exception.BookIsAlreadyBorrowedException;
import com.t0khyo.library.exception.BorrowingRecordNotFoundException;
import com.t0khyo.library.mapper.BorrowingRecordMapper;
import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;
import com.t0khyo.library.model.dto.common.ContactInfoDTO;
import com.t0khyo.library.model.dto.response.BookResponse;
import com.t0khyo.library.model.dto.response.PatronResponse;
import com.t0khyo.library.model.entity.Book;
import com.t0khyo.library.model.entity.BorrowingRecord;
import com.t0khyo.library.model.entity.ContactInfo;
import com.t0khyo.library.model.entity.Patron;
import com.t0khyo.library.repository.BookRepository;
import com.t0khyo.library.repository.BorrowingRecordRepository;
import com.t0khyo.library.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceImplTest {
    private final Long borrowingRecordId = 1L;
    private final Long bookId = 2L;
    private final Long patronId = 3L;
    // Mocks
    @InjectMocks
    private BorrowServiceImpl borrowService;
    @Mock
    private BorrowingRecordRepository borrowRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private BorrowingRecordMapper borrowingRecordMapper;

    // setUp
    private BorrowingRecord borrowingRecord;
    private BorrowingRecordDTO borrowingRecordDTO;
    private Book book;
    private BookResponse bookResponse;
    private Patron patron;
    private PatronResponse patronResponse;

    @BeforeEach
    void setUp() {
        setUpBookObjects();
        setUpPatronObjects();

        borrowingRecord = BorrowingRecord.builder()
                .id(borrowingRecordId)
                .book(book)
                .patron(patron)
                .borrowDate(LocalDate.of(2024, Month.JULY, 7))
                .returned(false)
                .build();

        borrowingRecordDTO = BorrowingRecordDTO.builder()
                .id(borrowingRecord.getId())
                .book(bookResponse)
                .patron(patronResponse)
                .borrowDate(borrowingRecord.getBorrowDate())
                .returned(borrowingRecord.isReturned())
                .build();

    }

    private void setUpBookObjects() {
        book = Book.builder()
                .id(bookId)
                .title("Unit Testing Book")
                .author("John Doe")
                .publicationDate(2004)
                .ISBN("978-3-16-148410-0")
                .borrowed(false)
                .build();

        bookResponse = BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationDate(book.getPublicationDate())
                .ISBN(book.getISBN())
                .borrowed(book.isBorrowed())
                .build();
    }

    private void setUpPatronObjects() {
        ContactInfo contactInfo = ContactInfo.builder()
                .phone("+1234567890")
                .email("John@doe.io")
                .address("JUnit Main St.")
                .build();

        ContactInfoDTO contactInfoDTO = ContactInfoDTO.builder()
                .phone(contactInfo.getPhone())
                .email(contactInfo.getEmail())
                .address(contactInfo.getAddress())
                .build();

        patron = Patron.builder()
                .id(patronId)
                .firstName("John")
                .lastName("Doe")
                .contactInfo(contactInfo)
                .build();

        patronResponse = PatronResponse.builder()
                .id(patron.getId())
                .firstName(patron.getFirstName())
                .lastName(patron.getLastName())
                .contactInfo(contactInfoDTO)
                .build();
    }

    @Test
    void borrowBook_shouldBorrowBookSuccessfully() {
        // Arrange
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.toDto(borrowingRecord)).thenReturn(borrowingRecordDTO);

        // Act
        BorrowingRecordDTO result = borrowService.borrowBook(bookId, patronId);

        // Assert
        assertTrue(book.isBorrowed());
        assertEquals(borrowingRecordDTO, result);

        verify(bookRepository, times(1)).findById(bookId);
        verify(patronRepository, times(1)).findById(patronId);
        verify(borrowRepository, times(1)).save(any(BorrowingRecord.class));
        verify(borrowingRecordMapper, times(1)).toDto(borrowingRecord);

        verifyNoMoreInteractions(bookRepository, patronRepository, borrowRepository, borrowingRecordMapper);
    }

    @Test
    void borrowBook_shouldThrowBookIsAlreadyBorrowedException() {
        // Arrange
        book.setBorrowed(true);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        // Act
        BookIsAlreadyBorrowedException exception = assertThrows(
                BookIsAlreadyBorrowedException.class,
                () -> borrowService.borrowBook(bookId, patronId));

        // Assert
        String expectedErrorMessage = MessageFormat.format("Book with id: {0} is already borrowed.", bookId);

        assertEquals(expectedErrorMessage, exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);

        verifyNoMoreInteractions(bookRepository, patronRepository);
        verifyNoInteractions(borrowRepository, borrowingRecordMapper);
    }

    @Test
    void returnBook_shouldReturnTheBook() {
        // Arrange
        when(borrowRepository.findByReturnedFalseAndBookIdAndPatronId(bookId, patronId))
                .thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordMapper.toDto(borrowingRecord)).thenReturn(borrowingRecordDTO);

        // Act
        BorrowingRecordDTO result = borrowService.returnBook(bookId, patronId);

        // Assert
        assertEquals(LocalDate.now(), borrowingRecord.getReturnDate());
        assertEquals(borrowingRecordDTO, result);
        assertTrue(borrowingRecord.isReturned());
        assertFalse(borrowingRecord.getBook().isBorrowed());

        verify(borrowRepository, times(1))
                .findByReturnedFalseAndBookIdAndPatronId(bookId, patronId);
        verify(borrowingRecordMapper, times(1)).toDto(borrowingRecord);

        verifyNoMoreInteractions(borrowRepository, borrowingRecordMapper);
        verifyNoInteractions(bookRepository, patronRepository);
    }

    @Test
    void returnBook_shouldThrowBorrowingRecordNotFoundException() {
        // Arrange
        when(borrowRepository.findByReturnedFalseAndBookIdAndPatronId(bookId, patronId)).thenReturn(Optional.empty());

        // Act
        BorrowingRecordNotFoundException exception = assertThrows(
                BorrowingRecordNotFoundException.class,
                () -> borrowService.returnBook(bookId, patronId));

        // Assert
        String expectedErrorMessage = "No active borrowing record found for this book and patron.";
        assertEquals(expectedErrorMessage, exception.getMessage());

        verify(borrowRepository, times(1)).findByReturnedFalseAndBookIdAndPatronId(bookId, patronId);
        verifyNoMoreInteractions(borrowRepository);

        verifyNoInteractions(bookRepository, patronRepository, borrowingRecordMapper);
    }
}