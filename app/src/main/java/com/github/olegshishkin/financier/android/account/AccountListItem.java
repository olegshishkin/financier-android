package com.github.olegshishkin.financier.android.account;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import java.util.Objects;

public class AccountListItem {

    public static final ItemCallback<AccountListItem> ITEM_CALLBACK = new ItemCallback<>() {

        @Override
        public boolean areItemsTheSame(@NonNull AccountListItem oldItem,
                                       @NonNull AccountListItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AccountListItem oldItem,
                                          @NonNull AccountListItem newItem) {
            return Objects.equals(oldItem.getName(), newItem.getName());
        }
    };

    private final String name;

    public AccountListItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
