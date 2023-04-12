package com.github.olegshishkin.financier.android.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.github.olegshishkin.financier.android.rest.service.AccountRestService;
import java.util.List;

public class AccountsViewModel extends AndroidViewModel {

    private final AccountRestService accountRestService;
    private final LiveData<List<AccountListItem>> getAllAccountsLiveData;

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        this.accountRestService = new AccountRestService();
        this.getAllAccountsLiveData = this.accountRestService.getGetAllAccountsLiveData();
    }

    public LiveData<List<AccountListItem>> getGetAllAccountsLiveData() {
        return this.getAllAccountsLiveData;
    }

    public void getAllAccounts() {
        this.accountRestService.getAllAccounts();
    }
}