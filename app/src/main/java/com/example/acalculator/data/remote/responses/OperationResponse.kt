package com.example.acalculator.data.remote.responses

import com.google.gson.annotations.SerializedName

data class OperationResponse(@SerializedName("message") private var message: String) {
}