package com.github.olegshishkin.financier.android.account;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AccountRestClient {

    @GET("/accounts")
    public Call<List<Account>> getAll();

}
