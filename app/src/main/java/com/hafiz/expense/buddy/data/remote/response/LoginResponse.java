package com.hafiz.expense.buddy.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("user_id")
    private String userId;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
}
