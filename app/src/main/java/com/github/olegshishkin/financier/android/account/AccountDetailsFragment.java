package com.github.olegshishkin.financier.android.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.olegshishkin.financier.android.databinding.FragmentAccountDetailsBinding;

public class AccountDetailsFragment extends Fragment {

    private static final String ARG_NAME = "name";

    private FragmentAccountDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = FragmentAccountDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var args = getArguments();
        if (args == null) {
            return;
        }

        var name = args.getString(ARG_NAME);
        var account = getAccount(name);
        this.binding.setAccount(account);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }

    @Deprecated
    private Account getAccount(@NonNull String name) {
        return new Account(name);
    }

    public static AccountDetailsFragment newInstance(@NonNull String name) {
        var args = new Bundle();
        args.putString(ARG_NAME, name);

        var fragment = new AccountDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}