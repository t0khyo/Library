package com.t0khyo.library.repository;

import com.t0khyo.library.model.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByReturnedFalseAndBookIdAndPatronId(Long bookId, Long patronId);
}
