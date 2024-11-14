package com.t0khyo.library.service;

import com.t0khyo.library.model.dto.request.BookRequest;
import com.t0khyo.library.model.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService extends CRUDService<BookRequest, BookResponse, Long> {

    Page<BookResponse> getPage(Pageable pageable);
}
