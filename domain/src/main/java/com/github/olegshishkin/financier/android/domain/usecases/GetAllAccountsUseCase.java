package com.github.olegshishkin.financier.android.domain.usecases;

import com.github.olegshishkin.financier.android.domain.model.Account;
import com.github.olegshishkin.financier.android.domain.repositories.AccountRepository;
import io.reactivex.Observable;
import java.util.List;

public class GetAllAccountsUseCase {

    private final AccountRepository accountRepository;

    public GetAllAccountsUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Observable<List<Account>> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }
}
