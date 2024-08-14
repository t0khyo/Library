package com.t0khyo.library.mapper;

import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;
import com.t0khyo.library.model.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring", uses={PatronMapper.class})
public interface BorrowingRecordMapper {
    BorrowingRecord toEntity(BorrowingRecordDTO borrowingRecordDTO);

    @Mapping(target="book.borrowingRecords", ignore=true)
    BorrowingRecordDTO toDto(BorrowingRecord borrowingRecord);

    @Mapping(target="id", ignore=true)
    void update(BorrowingRecordDTO dto, @MappingTarget BorrowingRecord entity);
}
