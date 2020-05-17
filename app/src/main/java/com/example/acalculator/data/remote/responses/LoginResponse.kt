package com.example.acalculator.data.remote.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email") private var email: String,
    @SerializedName("token") private var token: String
) {
    fun getToken(): String {
        return token
    }
}