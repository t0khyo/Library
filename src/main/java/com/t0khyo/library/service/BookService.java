package com.t0khyo.library.service;

import com.t0khyo.library.model.dto.request.BookRequest;
import com.t0khyo.library.model.dto.response.BookResponse;

public interface BookService extends CRUDService<BookRequest, BookResponse, Long> {

}
