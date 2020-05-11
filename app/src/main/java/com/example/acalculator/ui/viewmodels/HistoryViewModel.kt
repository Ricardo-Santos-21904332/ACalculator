package com.example.acalculator.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.acalculator.data.local.room.CalculatorDatabase
import com.example.acalculator.domain.calculator.CalculatorLogic
import com.example.acalculator.ui.listeners.OnListChanged

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val storage = CalculatorDatabase.getInstance(application).operationDao()
    private val calculatorLogic =
        CalculatorLogic(storage)
    var lista = calculatorLogic.getList()

    private var listener: OnListChanged? = null

    private fun notifyOnListChanged() {
        listener?.onListChanged(lista)
    }

    fun registerListListener(listener: OnListChanged) {
        this.listener = listener
        listener.onListChanged(lista)
    }

    fun unregisterListListener() {
        listener = null
    }

    fun removerLista(posicao: Int) {
        calculatorLogic.apagarDaLista(posicao)
        notifyOnListChanged()
    }
}