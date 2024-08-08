package com.t0khyo.library.repository;

import com.t0khyo.library.model.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
