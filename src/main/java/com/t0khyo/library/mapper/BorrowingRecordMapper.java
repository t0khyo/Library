package com.t0khyo.library.mapper;

import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;
import com.t0khyo.library.model.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring", uses={BookMapper.class, PatronMapper.class})
public interface BorrowingRecordMapper {
    BorrowingRecord toEntity(BorrowingRecordDTO borrowingRecordDTO);

    BorrowingRecordDTO toDto(BorrowingRecord borrowingRecord);

    void update(BorrowingRecordDTO dto, @MappingTarget BorrowingRecord entity);
}
