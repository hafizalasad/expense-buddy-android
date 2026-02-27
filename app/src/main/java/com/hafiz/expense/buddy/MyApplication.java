package com.hafiz.expense.buddy;

import android.app.Application;

import com.hafiz.expense.buddy.data.remote.api.auth.AuthApiService;
import com.hafiz.expense.buddy.data.remote.retrofit.RetrofitClient;
import com.hafiz.expense.buddy.data.repository.AuthRepository;

public class MyApplication extends Application {

    private AuthRepository authRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        AuthApiService authApiService = RetrofitClient.getInstance()
                .create(AuthApiService.class);

        authRepository = new AuthRepository(authApiService);
    }

    public AuthRepository getAuthRepository() {
        return authRepository;
    }
}
