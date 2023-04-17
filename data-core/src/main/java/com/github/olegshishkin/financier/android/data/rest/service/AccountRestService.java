package com.github.olegshishkin.financier.android.data.rest.service;

import com.github.olegshishkin.financier.android.data.rest.client.Financier;
import com.github.olegshishkin.financier.android.domain.model.Account;
import com.github.olegshishkin.financier.android.domain.repositories.AccountRepository;
import com.github.olegshishkin.financier.api.AccountsApi;
import com.github.olegshishkin.financier.api.dto.AccountOutputDto;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class AccountRestService implements AccountRepository {

    private final AccountsApi accountsClient;

    public AccountRestService() {
        this.accountsClient = Financier.client();
    }

    @Override
    public Observable<List<Account>> getAllAccounts() {
        return accountsClient.getAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(x -> x)
                .map(AccountOutputDto::getName)
                .map(Account::new)
                .toList()
                .toObservable();
    }
}
