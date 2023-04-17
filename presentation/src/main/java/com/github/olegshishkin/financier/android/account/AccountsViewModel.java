package com.github.olegshishkin.financier.android.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.github.olegshishkin.financier.android.domain.model.Account;
import com.github.olegshishkin.financier.android.domain.usecases.GetAllAccountsUseCase;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;

public class AccountsViewModel extends AndroidViewModel {

    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final MutableLiveData<List<AccountListItem>> getAllAccountsLiveData;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AccountsViewModel(@NonNull Application application,
                             GetAllAccountsUseCase getAllAccountsUseCase) {
        super(application);
        this.getAllAccountsUseCase = getAllAccountsUseCase;
        this.getAllAccountsLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<AccountListItem>> getGetAllAccountsLiveData() {
        return getAllAccountsLiveData;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void getAllAccounts() {
        var disposable = getAllAccountsUseCase.getAllAccounts()
                .flatMapIterable(x -> x)
                .map(Account::getName)
                .map(AccountListItem::new)
                .toList()
                .toObservable()
                .subscribe(this::onAccountsReceived, this::onError);
        compositeDisposable.add(disposable);
    }

    private void onAccountsReceived(List<AccountListItem> items) {
        getAllAccountsLiveData.postValue(items);
    }

    private void onError(Throwable t) {
        getAllAccountsLiveData.postValue(null);
    }
}
