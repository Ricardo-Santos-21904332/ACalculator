package com.example.acalculator.domain.register

import com.example.acalculator.data.remote.requests.Register
import com.example.acalculator.data.remote.services.RegisterService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

var registadoComSucesso = false

class RegisterLogic(private val retrofit: Retrofit) {

    fun resgiterUser(name: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val service = retrofit.create(RegisterService::class.java)
            val response = service.register(
                Register(
                    name,
                    email,
                    password
                )
            )
            if (response.isSuccessful) {
                registadoComSucesso = true
            }
        }
    }
}