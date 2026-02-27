package com.hafiz.expense.buddy.ui.login;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hafiz.expense.buddy.data.remote.dto.request.LoginRequestDto;
import com.hafiz.expense.buddy.data.remote.response.LoginResponse;
import com.hafiz.expense.buddy.data.repository.AuthRepository;
import com.hafiz.expense.buddy.utils.Resource;

public class LoginViewModel extends ViewModel {

    private final AuthRepository repository;

    private final ObservableField<String> email = new ObservableField<>();

    private final ObservableField<String> password = new ObservableField<>();
    private final MutableLiveData<Resource<LoginResponse>> loginState = new MutableLiveData<>();

    public LoginViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<LoginResponse>> getLoginState() {
        return loginState;
    }


    public void login(String email, String password) {
        LoginRequestDto requestDto = new LoginRequestDto(email, password);
        repository.login(requestDto).observeForever(resource -> {
            loginState.setValue(resource);
        });
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final AuthRepository repository;

        public Factory(AuthRepository repository) {
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(LoginViewModel.class)) {
                return (T) new LoginViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}