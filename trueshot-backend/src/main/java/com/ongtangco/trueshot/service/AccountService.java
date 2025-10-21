package com.ongtangco.trueshot.service;

import com.ongtangco.trueshot.dto.AccountRequest;
import com.ongtangco.trueshot.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAll();
    Account getById(Integer id);
    Account save(AccountRequest request);
    void delete(Integer id);
}
