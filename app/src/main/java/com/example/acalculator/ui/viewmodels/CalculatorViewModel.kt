package com.example.acalculator.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.acalculator.data.local.room.CalculatorDatabase
import com.example.acalculator.data.remote.RetrofitBuilder
import com.example.acalculator.data.repositories.OperationRepository
import com.example.acalculator.domain.calculator.CalculatorLogic
import com.example.acalculator.ui.listeners.OnDisplayChanged
import com.example.acalculator.ui.listeners.OnListChanged
import com.example.acalculator.entities.Operation

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    private val storage = CalculatorDatabase.getInstance(application).operationDao()
    private val operationRepository =
        OperationRepository(storage, RetrofitBuilder.getInstance(ENDPOINT))
    private val calculatorLogic = CalculatorLogic(operationRepository)
    var display: String = "0"
    private var listener: OnDisplayChanged? = null

    private fun notifyOnDisplayChanged() {
        listener?.onDisplayChanged(display)
    }

    fun registerListener(listener: OnDisplayChanged) {
        this.listener = listener
        listener.onDisplayChanged(display)
    }

    fun registerListListener(listener: OnListChanged) {
        calculatorLogic.registerListener(listener)
        listener.onListChanged(calculatorLogic.getAll() as MutableList<Operation>)
    }

    fun unregisterListListener() {
        calculatorLogic.unregisterListener()
    }

    fun unregisterListener() {
        listener = null
    }

    fun onClickSymbol(symbol: String) {
        display = calculatorLogic.insertSymbol(display, symbol)
        notifyOnDisplayChanged()
    }

    fun onClickEqualsOnline() {
        display = calculatorLogic.performOperationOnline(display).toString()
        notifyOnDisplayChanged()
    }

    fun onClickEqualsOffline() {
        display = calculatorLogic.performOperationLocal(display).toString()
        notifyOnDisplayChanged()
    }

    fun getList(): MutableList<Operation> {
        return calculatorLogic.getAll() as MutableList<Operation>
    }

    fun enviarOperacoesNaoEnviadas() {
        calculatorLogic.enviarOperacoesNaoEnviadas()
    }

    fun removerLista() {
        calculatorLogic.apagarDaLista()
    }
}