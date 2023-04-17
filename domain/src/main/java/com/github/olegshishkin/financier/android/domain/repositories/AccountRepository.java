package com.github.olegshishkin.financier.android.domain.repositories;

import com.github.olegshishkin.financier.android.domain.model.Account;
import io.reactivex.Observable;
import java.util.List;

public interface AccountRepository {

    Observable<List<Account>> getAllAccounts();
}
