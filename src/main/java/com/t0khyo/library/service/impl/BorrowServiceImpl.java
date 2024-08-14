package com.t0khyo.library.service.impl;

import com.t0khyo.library.exception.BookIsAlreadyBorrowedException;
import com.t0khyo.library.exception.BookNotFoundException;
import com.t0khyo.library.exception.BorrowingRecordNotFoundException;
import com.t0khyo.library.exception.PatronNotFoundException;
import com.t0khyo.library.mapper.BorrowingRecordMapper;
import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;
import com.t0khyo.library.model.entity.Book;
import com.t0khyo.library.model.entity.BorrowingRecord;
import com.t0khyo.library.model.entity.Patron;
import com.t0khyo.library.repository.BookRepository;
import com.t0khyo.library.repository.BorrowingRecordRepository;
import com.t0khyo.library.repository.PatronRepository;
import com.t0khyo.library.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowingRecordRepository borrowRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordMapper borrowingRecordMapper;

    @Override
    @Transactional
    public BorrowingRecordDTO borrowBook(Long bookId, Long patronId) throws BookIsAlreadyBorrowedException {
        Book book = findBookById(bookId);
        Patron patron = findPatronById(patronId);

        if (book.isBorrowed()) {
            throw new BookIsAlreadyBorrowedException("The book with id: " + bookId + " is already borrowed.");
        }

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowDate(LocalDate.now())
                .returned(false)
                .build();

        BorrowingRecord savedBorrowingRecord = borrowRepository.save(borrowingRecord);
        book.setBorrowed(true);

        return borrowingRecordMapper.toDto(savedBorrowingRecord);
    }

    @Override
    @Transactional
    public BorrowingRecordDTO returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowRepository
                .findByReturnedFalseAndBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new BorrowingRecordNotFoundException("No active borrowing record found for this book and patron."));

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecord.setReturned(true);
        borrowingRecord.getBook().setBorrowed(false);

        return borrowingRecordMapper.toDto(borrowingRecord);
    }

    // --- private methods ---
    private Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with Id: " + id + " not found."));
    }

    private Patron findPatronById(Long id) {
        return patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException("Patron with Id: " + id + " not found."));
    }
}
