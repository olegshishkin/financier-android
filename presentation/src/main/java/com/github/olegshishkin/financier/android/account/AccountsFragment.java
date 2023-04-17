package com.github.olegshishkin.financier.android.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.github.olegshishkin.financier.android.R;
import com.github.olegshishkin.financier.android.databinding.FragmentAccountsBinding;

public class AccountsFragment extends Fragment {

    private FragmentAccountsBinding binding;
    private AccountsListAdapter adapter;
    private AccountsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new AccountsListAdapter(getLayoutInflater(), this::showDetails);
        this.viewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
        this.viewModel.getGetAllAccountsLiveData()
                .observe(this, items -> this.adapter.submitList(items));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = FragmentAccountsBinding.inflate(inflater, container, false);
        this.binding.accountsList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.binding.accountsList.setAdapter(this.adapter);
        return this.binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.viewModel.getAllAccounts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.viewModel.getCompositeDisposable().dispose();
        this.binding = null;
    }

    private void showDetails(AccountListItem item) {
        var replacer = AccountDetailsFragment.newInstance(item.getName());
        getParentFragmentManager().beginTransaction()
                // todo switch to binding
                .replace(R.id.fragment_container_view, replacer)
                .addToBackStack(null)
                .commit();
    }
}