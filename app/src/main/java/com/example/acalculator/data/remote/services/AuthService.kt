package com.example.acalculator.data.remote.services

import com.example.acalculator.data.remote.requests.Login
import com.example.acalculator.data.remote.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("users/login")
    suspend fun login(@Body body: Login) : Response<LoginResponse>
}