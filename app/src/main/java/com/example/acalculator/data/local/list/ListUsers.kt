package com.example.acalculator.data.local.list

import com.example.acalculator.entities.User

class ListUsers private constructor() {

    private val users = mutableListOf<User>()

    companion object {
        private var instance: ListUsers? = null

        fun getInstance(): ListUsers {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        ListUsers()
                }
                return instance as ListUsers
            }
        }
    }

    fun insert(user: User) {
        users.add(user)
    }

    fun getAll(): MutableList<User> {
        return users
    }
}