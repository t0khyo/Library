package com.t0khyo.library.service;

import java.util.List;

public interface CRUDService<REQUEST, RESPONSE, ID> {
    List<RESPONSE> getAll();

    RESPONSE getById(ID id);

    RESPONSE save(REQUEST request);

    RESPONSE update(ID id, REQUEST request);

    void deleteById(ID id);
}
