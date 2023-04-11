package com.github.olegshishkin.financier.android.account;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import java.util.function.Consumer;

public class AccountsListAdapter extends ListAdapter<AccountListItem, AccountsViewHolder> {

    private final LayoutInflater inflater;
    private final Consumer<AccountListItem> consumer;

    public AccountsListAdapter(@NonNull LayoutInflater inflater,
                               @NonNull Consumer<AccountListItem> consumer) {
        super(AccountListItem.ITEM_CALLBACK);
        this.inflater = inflater;
        this.consumer = consumer;
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AccountsViewHolder.create(inflater, parent, consumer);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
