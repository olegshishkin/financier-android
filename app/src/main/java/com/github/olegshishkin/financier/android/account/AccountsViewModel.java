package com.github.olegshishkin.financier.android.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

public class AccountsViewModel extends AndroidViewModel {

    private final LiveData<List<AccountListItem>> items;

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        this.items = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<AccountListItem>> getItems() {
        return items;
    }

    public void insert(AccountListItem item) {
        this.items.getValue().add(item);
    }
}