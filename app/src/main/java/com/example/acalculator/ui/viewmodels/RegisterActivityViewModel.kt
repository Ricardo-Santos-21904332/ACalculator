package com.example.acalculator.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.acalculator.data.local.list.ListUsers
import com.example.acalculator.data.remote.RetrofitBuilder
import com.example.acalculator.domain.auth.AuthLogic
import com.example.acalculator.domain.register.RegisterLogic
import com.example.acalculator.entities.User


class RegisterActivityViewModel : ViewModel() {
    private val registerLogic = RegisterLogic(
        RetrofitBuilder.getInstance(ENDPOINT)
    )

    fun onClickRegister(name: String, email: String, password: String) {
        registerLogic.resgiterUser(name, email, password)
    }
}