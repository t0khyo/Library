package com.t0khyo.library.controller;

import com.t0khyo.library.model.dto.request.PatronRequest;
import com.t0khyo.library.model.dto.response.PatronResponse;
import com.t0khyo.library.service.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/patrons")
//@RestController
public class PatronController {
    private final PatronService patronService;

    @GetMapping
    public ResponseEntity<List<PatronResponse>> getAllPatrons() {
        return ResponseEntity.ok(patronService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronResponse> getPatronById(@PathVariable Long id) {
        return ResponseEntity.ok(patronService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PatronResponse> savePatron(@RequestBody PatronRequest patronRequest) {
        PatronResponse patronResponse = patronService.save(patronRequest);
        URI patronURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patronResponse.id())
                .toUri();
        return ResponseEntity.created(patronURI).body(patronResponse);
    }

    @PutMapping
    public ResponseEntity<PatronResponse> updatePatron(
            @PathVariable Long id,
            @Valid @RequestBody PatronRequest patronRequest
    ) {
        return ResponseEntity.ok(patronService.update(id, patronRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
