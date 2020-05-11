package com.example.acalculator.ui.listeners

import com.example.acalculator.entities.Operation

interface OnListChanged {

    fun onListChanged(list: MutableList<Operation>?)
}