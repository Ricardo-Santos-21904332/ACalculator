package com.example.acalculator.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.acalculator.data.local.list.ListUsers
import com.example.acalculator.entities.User

class RegisterActivityViewModel : ViewModel() {
    private val listUsers =
        ListUsers.getInstance()

    fun insert(user: User) {
        listUsers.insert(user)
    }

}