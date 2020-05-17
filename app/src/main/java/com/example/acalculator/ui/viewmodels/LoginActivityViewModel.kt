package com.example.acalculator.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.acalculator.data.local.list.ListUsers
import com.example.acalculator.data.remote.RetrofitBuilder
import com.example.acalculator.domain.auth.AuthLogic

const val ENDPOINT = "https://cm-calculadora.herokuapp.com/api/"

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val listUsers = ListUsers.getInstance()
    private val authLogic = AuthLogic(
        RetrofitBuilder.getInstance(ENDPOINT), listUsers
    )

    fun onClickLogin(email: String, password: String) {
        authLogic.authenticateUser(email, password)
    }
}