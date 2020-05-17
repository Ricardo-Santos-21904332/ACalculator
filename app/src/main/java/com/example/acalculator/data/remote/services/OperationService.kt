package com.example.acalculator.data.remote.services

import com.example.acalculator.data.remote.responses.OperationResponse
import com.example.acalculator.entities.Operation
import retrofit2.Response
import retrofit2.http.*

interface OperationService {

    @POST("operations")
    suspend fun operationPost(@Header("Authorization")token: String, @Body body: Operation): Response<OperationResponse>

    @GET("operations")
    suspend fun operationGet(@Header("Authorization")token: String): Response<List<Operation>>

    @DELETE("operations")
    suspend fun operationDelete(@Header("Authorization")token: String): Response<OperationResponse>
}