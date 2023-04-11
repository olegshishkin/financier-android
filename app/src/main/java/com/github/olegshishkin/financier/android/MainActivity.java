package com.github.olegshishkin.financier.android;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.olegshishkin.financier.android.account.AccountsFragment;
import com.github.olegshishkin.financier.android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            //todo
//            var binding = ActivityMainBinding.inflate(getLayoutInflater());
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(binding.fragmentContainerView.getId(), AccountsFragment.class, null)
                    .commit();
        }
    }
}