package com.ongtangco.trueshot.controller;

import com.ongtangco.trueshot.dto.AccountRequest;
import com.ongtangco.trueshot.model.Account;
import com.ongtangco.trueshot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> list() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> get(@PathVariable Integer id) {
        Account account = accountService.getById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody AccountRequest request) {
        Account account = accountService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable Integer id,
                                          @Valid @RequestBody AccountRequest request) {
        request.setId(id);
        Account account = accountService.save(request);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
