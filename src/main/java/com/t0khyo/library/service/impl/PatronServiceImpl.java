package com.t0khyo.library.service.impl;

import com.t0khyo.library.exception.PatronNotFoundException;
import com.t0khyo.library.mapper.PatronMapper;
import com.t0khyo.library.model.dto.request.PatronRequest;
import com.t0khyo.library.model.dto.response.PatronResponse;
import com.t0khyo.library.model.entity.Patron;
import com.t0khyo.library.repository.PatronRepository;
import com.t0khyo.library.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {
    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper;

    @Override
    public List<PatronResponse> getAll() {
        return patronRepository.findAll().stream().map(patronMapper::toDto).toList();
    }

    @Override
    public PatronResponse getById(Long id) {
        return patronMapper.toDto(findPatronById(id));
    }

    @Override
    @Transactional
    public PatronResponse save(PatronRequest patronRequest) {
        Patron patron = patronMapper.toEntity(patronRequest);
        Patron savedPatron = patronRepository.save(patron);
        return patronMapper.toDto(savedPatron);
    }

    @Override
    @Transactional
    public PatronResponse update(Long id, PatronRequest patronRequest) {
        Patron existingPatron = findPatronById(id);
        patronMapper.update(patronRequest, existingPatron);
        return patronMapper.toDto(existingPatron);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Patron patron = findPatronById(id);
        patronRepository.delete(patron);
    }

    // --- private methods ---
    private Patron findPatronById(Long id) {
        return patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException("Patron with Id: " + id + " not found."));
    }
}
