package com.example.acalculator.data.remote.requests

data class Register(
    private val name: String,
    private val email: String,
    private val password: String
) {
}