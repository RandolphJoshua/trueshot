package com.ongtangco.trueshot.controller;

import com.ongtangco.trueshot.dto.AppDetailsRequest;
import com.ongtangco.trueshot.model.AppDetails;
import com.ongtangco.trueshot.service.AppDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/app-details")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Validated
public class AppDetailsController {

    private final AppDetailsService appDetailsService;

    @GetMapping
    public ResponseEntity<List<AppDetails>> list() {
        return ResponseEntity.ok(appDetailsService.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<AppDetails> active() {
        AppDetails details = appDetailsService.getActiveDetails();
        if (details == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(details);
    }

    @PostMapping
    public ResponseEntity<AppDetails> save(@Valid @RequestBody AppDetailsRequest request) {
        AppDetails saved = appDetailsService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppDetails> update(@PathVariable Integer id,
                                             @Valid @RequestBody AppDetailsRequest request) {
        request.setId(id);
        AppDetails saved = appDetailsService.save(request);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        appDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
