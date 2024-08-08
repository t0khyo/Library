package com.t0khyo.library.mapper;

import com.t0khyo.library.model.dto.request.PatronRequest;
import com.t0khyo.library.model.dto.response.PatronResponse;
import com.t0khyo.library.model.entity.Patron;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface PatronMapper {
    Patron toEntity(PatronRequest patronRequest);

    PatronResponse toDto(Patron patron);

    void update(PatronRequest patronRequest, @MappingTarget Patron patron);
}
