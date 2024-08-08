package com.t0khyo.library.controller;

import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;
import com.t0khyo.library.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BorrowController {
    private final BorrowService borrowService;
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId
    ) {
        return ResponseEntity.ok(borrowService.borrowBook(bookId, patronId));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId
    ) {
        return ResponseEntity.ok(borrowService.returnBook(bookId, patronId));
    }
}
