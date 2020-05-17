package com.example.acalculator.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val nome: String,
    val email: String,
    val password: String,
    @PrimaryKey val token: String
) {
}