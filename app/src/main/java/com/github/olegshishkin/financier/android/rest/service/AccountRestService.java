package com.github.olegshishkin.financier.android.rest.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.github.olegshishkin.financier.android.account.AccountListItem;
import com.github.olegshishkin.financier.android.rest.client.Financier;
import com.github.olegshishkin.financier.api.AccountsApi;
import com.github.olegshishkin.financier.api.model.AccountOutputDTO;
import java.util.List;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRestService {

    private final AccountsApi accountsClient;
    private final MutableLiveData<List<AccountListItem>> getAllAccountsLiveData;

    public AccountRestService() {
        this.accountsClient = Financier.client();
        this.getAllAccountsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<AccountListItem>> getGetAllAccountsLiveData() {
        return this.getAllAccountsLiveData;
    }

    public void getAllAccounts() {
        this.accountsClient.getAllAccounts()
                .enqueue(new Callback<>() {

                    @Override
                    public void onResponse(Call<List<AccountOutputDTO>> call,
                                           Response<List<AccountOutputDTO>> rs) {
                        if (rs.body() != null) {
                            var items = rs.body()
                                    .stream()
                                    .map(AccountOutputDTO::getName)
                                    .map(AccountListItem::new)
                                    .collect(Collectors.toList());
                            AccountRestService.this.getAllAccountsLiveData.postValue(items);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AccountOutputDTO>> call, Throwable t) {
                        AccountRestService.this.getAllAccountsLiveData.postValue(null);
                    }
                });
    }
}
