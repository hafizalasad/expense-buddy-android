package com.hafiz.expense.buddy.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hafiz.expense.buddy.data.remote.api.auth.AuthApiService;
import com.hafiz.expense.buddy.data.remote.dto.request.LoginRequestDto;
import com.hafiz.expense.buddy.data.remote.response.BaseResponse;
import com.hafiz.expense.buddy.data.remote.response.LoginResponse;
import com.hafiz.expense.buddy.utils.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final AuthApiService api;

    public AuthRepository(AuthApiService api) {
        this.api = api;
    }

    public LiveData<Resource<LoginResponse>> login(LoginRequestDto loginRequestDto) {
        MutableLiveData<Resource<LoginResponse>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        api.login(loginRequestDto)
                .enqueue(new Callback<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<LoginResponse>> call, Response<BaseResponse<LoginResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            BaseResponse<LoginResponse> body = response.body();
                            if (body.isSuccess()) {
                                result.setValue(Resource.success(body.getData()));
                            } else {
                                result.setValue(Resource.error(body.getMessage()));
                            }
                        } else {
                            result.setValue(Resource.error("Server error: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<LoginResponse>> call, Throwable t) {
                        result.setValue(Resource.error(t.getMessage()));
                    }
                });

        return result;
    }
}