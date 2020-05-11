package com.example.acalculator.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.acalculator.data.local.room.CalculatorDatabase
import com.example.acalculator.domain.calculator.CalculatorLogic
import com.example.acalculator.ui.listeners.OnDisplayChanged
import com.example.acalculator.ui.listeners.OnListChanged
import com.example.acalculator.entities.Operation

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    var display: String = "0"
    private var listener: OnDisplayChanged? = null
    private var listenerList: OnListChanged? = null
    private val storage = CalculatorDatabase.getInstance(application).operationDao()
    private val calculatorLogic =
        CalculatorLogic(storage)


    private fun notifyOnDisplayChanged() {
        listener?.onDisplayChanged(display)
    }

    private fun notifyOnListChanged() {
        listenerList?.onListChanged(getList())
    }

    fun registerListener(listener: OnDisplayChanged) {
        this.listener = listener
        listener.onDisplayChanged(display)
    }

    fun registerListListenerList(listener: OnListChanged) {
        this.listenerList = listener
        listener.onListChanged(getList())
    }

    fun unregisterListener() {
        listener = null
    }

    fun unregisterListListener() {
        listenerList = null
    }

    fun onClickSymbol(symbol: String) {
        display = calculatorLogic.insertSymbol(display, symbol)
        notifyOnDisplayChanged()
    }

    fun onClickEquals() {
        display = calculatorLogic.performOperation(display).toString()
        notifyOnDisplayChanged()
        notifyOnListChanged()
    }

    fun getList(): MutableList<Operation> {
        return calculatorLogic.getList()
    }

    fun removerLista(posicao: Int) {
        calculatorLogic.apagarDaLista(posicao)
        notifyOnListChanged()
    }
}