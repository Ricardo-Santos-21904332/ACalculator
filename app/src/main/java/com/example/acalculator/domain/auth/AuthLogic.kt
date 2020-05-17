package com.example.acalculator.domain.auth

import com.example.acalculator.data.local.list.ListUsers
import com.example.acalculator.data.remote.requests.Login
import com.example.acalculator.data.remote.services.AuthService
import com.example.acalculator.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit


var token = ""
var sucesso = false

class AuthLogic(private val retrofit: Retrofit, private val listaUsers: ListUsers) {

    fun authenticateUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val service = retrofit.create(AuthService::class.java)
            val response = service.login(
                Login(
                    email,
                    password
                )
            )
            if (response.isSuccessful) {
                token = response.body()!!.getToken()
                listaUsers.insert(
                    User(
                        email,
                        email,
                        password,
                        token
                    )
                )
                sucesso = true
            } else {
                sucesso = false
            }
        }
    }


}