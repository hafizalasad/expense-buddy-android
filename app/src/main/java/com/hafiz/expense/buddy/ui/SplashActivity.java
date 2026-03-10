package com.hafiz.expense.buddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.hafiz.expense.buddy.MainActivity;
import com.hafiz.expense.buddy.MyApplication;
import com.hafiz.expense.buddy.R;
import com.hafiz.expense.buddy.ui.login.LoginActivity;
import com.hafiz.expense.buddy.utils.TokenManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // must be called BEFORE super.onCreate()
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

//        TokenManager tokenManager = ((MyApplication) getApplication()).getTokenManager();
//
//        if (tokenManager.hasToken()) {
//            startActivity(new Intent(this, MainActivity.class));
//        } else {
//            startActivity(new Intent(this, LoginActivity.class));
//        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
