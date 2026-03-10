package com.hafiz.expense.buddy;

import android.app.Application;

import com.hafiz.expense.buddy.data.remote.api.auth.AuthApiService;
import com.hafiz.expense.buddy.data.remote.retrofit.RetrofitClient;
import com.hafiz.expense.buddy.data.repository.AuthRepository;
import com.hafiz.expense.buddy.utils.TokenManager;

public class MyApplication extends Application {
    private AuthRepository authRepository;
    private TokenManager tokenManager;

    @Override
    public void onCreate() {
        super.onCreate();

        tokenManager = new TokenManager(this);

        AuthApiService authApiService = RetrofitClient.getInstance()
                .create(AuthApiService.class);

        authRepository = new AuthRepository(authApiService);
    }

    public AuthRepository getAuthRepository() { return authRepository; }
    public TokenManager getTokenManager() { return tokenManager; }
}
