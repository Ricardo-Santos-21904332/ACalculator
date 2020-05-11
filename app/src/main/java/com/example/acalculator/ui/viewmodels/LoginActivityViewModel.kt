package com.example.acalculator.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.acalculator.data.local.list.ListUsers
import com.example.acalculator.data.remote.RetrofitBuilder
import com.example.acalculator.ui.activities.LoginActivity

const val ENDPOINT = "https://cm-calculadroa.herokuapp.com/api/"

class LoginActivityViewModel : ViewModel() {

     val loginActivity = RetrofitBuilder.getInstance(ENDPOINT)

}