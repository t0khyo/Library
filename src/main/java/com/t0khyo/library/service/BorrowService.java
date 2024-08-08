package com.t0khyo.library.service;

import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;

public interface BorrowService {
    BorrowingRecordDTO borrowBook(Long bookId, Long patronId);

    BorrowingRecordDTO returnBook(Long bookId, Long patronId);
}
