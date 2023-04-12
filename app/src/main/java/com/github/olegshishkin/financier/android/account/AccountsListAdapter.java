package com.github.olegshishkin.financier.android.account;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.github.olegshishkin.financier.android.account.AccountsListAdapter.AccountsViewHolder;
import com.github.olegshishkin.financier.android.databinding.AccountsListItemBinding;
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
        return AccountsViewHolder.create(this.inflater, parent, this.consumer);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class AccountsViewHolder extends RecyclerView.ViewHolder {

        private final AccountsListItemBinding binding;

        public AccountsViewHolder(@NonNull AccountsListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AccountListItem item) {
            this.binding.setItem(item);
            this.binding.executePendingBindings();
        }

        public static AccountsViewHolder create(@NonNull LayoutInflater inflater,
                                                @NonNull ViewGroup parent,
                                                @NonNull Consumer<AccountListItem> consumer) {
            var binding = AccountsListItemBinding.inflate(inflater, parent, false);
            binding.getRoot().setOnClickListener(v -> consumer.accept(binding.getItem()));
            return new AccountsViewHolder(binding);
        }
    }
}
