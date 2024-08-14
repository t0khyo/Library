package com.t0khyo.library.mapper;

import com.t0khyo.library.model.dto.request.BookRequest;
import com.t0khyo.library.model.dto.response.BookResponse;
import com.t0khyo.library.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring", uses={BorrowingRecordMapper.class})
public interface BookMapper {
    Book toEntity(BookRequest bookRequest);

    @Mapping(target="borrowingRecords", ignore=true)
    BookResponse toDto(Book book);

    BookResponse toDetailedDto(Book book);

    @Mapping(target="id", ignore=true)
    void update(BookRequest dto, @MappingTarget Book entity);
}
