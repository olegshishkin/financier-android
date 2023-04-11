package com.github.olegshishkin.financier.android.account;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.olegshishkin.financier.android.databinding.AccountsListItemBinding;
import java.util.function.Consumer;

public class AccountsViewHolder extends RecyclerView.ViewHolder {

    private final AccountsListItemBinding binding;

    public AccountsViewHolder(@NonNull AccountsListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(AccountListItem item) {
        binding.setItem(item);
        binding.executePendingBindings();
    }

    public static AccountsViewHolder create(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent,
                                            @NonNull Consumer<AccountListItem> consumer) {
        var binding = AccountsListItemBinding.inflate(inflater, parent, false);
        binding.getRoot().setOnClickListener(v -> consumer.accept(binding.getItem()));
        return new AccountsViewHolder(binding);
    }
}
