package com.hafiz.expense.buddy.ui.login;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hafiz.expense.buddy.MyApplication;
import com.hafiz.expense.buddy.data.repository.AuthRepository;
import com.hafiz.expense.buddy.databinding.LoginActivityBinding;
import com.hafiz.expense.buddy.ui.signUp.SignupDialogFragment;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Manual DI — get repository from App, create ViewModel via factory
        AuthRepository repository = ((MyApplication) getApplication()).getAuthRepository();
        LoginViewModel.Factory factory = new LoginViewModel.Factory(repository);
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        // DataBinding connection
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);


        initListener();
        subscribeUi();
    }


    private void initListener() {

        binding.btnLogin.setOnClickListener(v -> {
            String email = viewModel.getEmail().get();
            String password = viewModel.getPassword().get();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.login(email, password);
        });

        binding.tvCreate.setOnClickListener(v -> {
            SignupDialogFragment dialog = new SignupDialogFragment();
            dialog.show(getSupportFragmentManager(), "SignupDialog");
        });

    }

    private void subscribeUi() {
        viewModel.getLoginState().observe(this, success -> {
            if (success == null) return;

            if (success.status != null) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Enter email first", Toast.LENGTH_SHORT).show();
            }
        });
    }
}