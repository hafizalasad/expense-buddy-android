package com.hafiz.expense.buddy.data.remote.dto.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequestDto {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
