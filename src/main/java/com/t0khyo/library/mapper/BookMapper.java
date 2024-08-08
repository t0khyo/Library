package com.t0khyo.library.mapper;

import com.t0khyo.library.model.dto.request.BookRequest;
import com.t0khyo.library.model.dto.response.BookResponse;
import com.t0khyo.library.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface BookMapper {
    Book toEntity(BookRequest bookRequest);

    BookResponse toDto(Book book);

    void update(BookRequest dto, @MappingTarget Book entity);
}
