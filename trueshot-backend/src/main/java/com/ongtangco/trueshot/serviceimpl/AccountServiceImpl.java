package com.ongtangco.trueshot.serviceimpl;

import com.ongtangco.trueshot.dto.AccountRequest;
import com.ongtangco.trueshot.entity.AccountData;
import com.ongtangco.trueshot.model.Account;
import com.ongtangco.trueshot.repository.AccountDataRepository;
import com.ongtangco.trueshot.service.AccountService;
import com.ongtangco.trueshot.util.Transform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountDataRepository repository;
    private final Transform<AccountData, Account> toModel = new Transform<>(Account.class);
    private final Transform<Account, AccountData> toEntity = new Transform<>(AccountData.class);

    @Override
    public List<Account> getAll() {
        return repository.findAll().stream().map(toModel::transform).collect(Collectors.toList());
    }

    @Override
    public Account getById(Integer id) {
        return repository.findById(id).map(toModel::transform).orElse(null);
    }

    @Override
    public Account save(AccountRequest request) {
        repository.findByUsernameIgnoreCase(request.getUsername())
                .filter(account -> request.getId() == null || !account.getId().equals(request.getId()))
                .ifPresent(account -> { throw new IllegalArgumentException("Username already exists"); });
        repository.findByEmailIgnoreCase(request.getEmail())
                .filter(account -> request.getId() == null || !account.getId().equals(request.getId()))
                .ifPresent(account -> { throw new IllegalArgumentException("Email already exists"); });

        Account account = new Account();
        account.setId(request.getId());
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setFullName(request.getFullName());
        account.setRole(StringUtils.hasText(request.getRole()) ? request.getRole() : "ADMIN");
        account.setNotes(request.getNotes());

        AccountData entity = toEntity.transform(account);
        AccountData saved = repository.save(entity);
        return toModel.transform(saved);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
