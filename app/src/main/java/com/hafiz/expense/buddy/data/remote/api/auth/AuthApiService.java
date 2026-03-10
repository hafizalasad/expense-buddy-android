package com.hafiz.expense.buddy.data.remote.api.auth;

import com.hafiz.expense.buddy.data.remote.dto.request.LoginRequestDto;
import com.hafiz.expense.buddy.data.remote.response.BaseResponse;
import com.hafiz.expense.buddy.data.remote.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/login")
    Call<BaseResponse<LoginResponse>> login(@Body LoginRequestDto request);
}
