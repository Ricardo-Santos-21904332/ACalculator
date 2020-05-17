package com.example.acalculator.data.remote.services

import com.example.acalculator.data.remote.requests.Register
import com.example.acalculator.data.remote.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("users/register")
    suspend fun register(@Body body: Register): Response<LoginResponse>
}