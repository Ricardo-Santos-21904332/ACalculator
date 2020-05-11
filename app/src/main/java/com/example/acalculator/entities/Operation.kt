package com.example.acalculator.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Operation(val expression: String, val result: Double) {

    @PrimaryKey
    var uuid: String = UUID.randomUUID().toString()
}