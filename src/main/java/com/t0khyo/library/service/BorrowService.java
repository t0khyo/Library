package com.t0khyo.library.service;

import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;
import com.t0khyo.library.exception.BookIsAlreadyBorrowedException;

public interface BorrowService {
    BorrowingRecordDTO borrowBook(Long bookId, Long patronId) throws BookIsAlreadyBorrowedException;

    BorrowingRecordDTO returnBook(Long bookId, Long patronId);
}
