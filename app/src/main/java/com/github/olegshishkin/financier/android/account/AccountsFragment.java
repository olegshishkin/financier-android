package com.github.olegshishkin.financier.android.account;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.github.olegshishkin.financier.android.R;
import com.github.olegshishkin.financier.android.databinding.FragmentAccountsBinding;
import com.github.olegshishkin.financier.android.rest.client.Financier;
import com.github.olegshishkin.financier.api.model.AccountOutputDTO;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsFragment extends Fragment {

    private FragmentAccountsBinding binding;
    private Call<List<AccountOutputDTO>> getAllAccountsApiCall;
    private AccountsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.getAllAccountsApiCall = Financier.client().getAllAccounts();
        this.binding = FragmentAccountsBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
        binding.accountsList.setLayoutManager(new LinearLayoutManager(getContext()));

        var adapter = new AccountsListAdapter(getLayoutInflater(), this::showDetails);
        binding.accountsList.setAdapter(adapter);
        viewModel.getItems().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    @Override
    public void onResume() {
        super.onResume();
        populate(viewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    private void populate(AccountsViewModel viewModel) {
        getAllAccountsApiCall.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<List<AccountOutputDTO>> call,
                                   @NonNull Response<List<AccountOutputDTO>> rs) {
                if (rs.isSuccessful()) {
                    if (rs.body() != null) {
                        Toast.makeText(getContext(), rs.body().toString(), LENGTH_SHORT).show();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        rs.body()
                                .forEach(e -> viewModel.insert(new AccountListItem(e.getName())));
                        return;
                    }
                    String msg = "Body is null";
                    Toast.makeText(getContext(), msg, LENGTH_SHORT).show();
                    Log.d("TAG", msg);
                }
                try (var body = rs.errorBody()) {
                    if (body != null) {
                        String msg = "REST call has been failed";
                        Toast.makeText(getContext(), msg, LENGTH_SHORT).show();
                        Log.d("TAG", msg + " :" + body.string());
                        return;
                    }
                    String msg = "Body is null";
                    Toast.makeText(getContext(), msg, LENGTH_SHORT).show();
                    Log.d("TAG", msg);
                } catch (IOException e) {
                    String msg = "REST call has been failed";
                    Toast.makeText(getContext(), msg, LENGTH_SHORT).show();
                    Log.d("TAG", msg, e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AccountOutputDTO>> call,
                                  @NonNull Throwable t) {
                String msg = "REST call has been failed";
                Toast.makeText(getContext(), msg, LENGTH_SHORT).show();
                Log.d("TAG", msg, t);
                call.cancel();
            }
        });
    }
}