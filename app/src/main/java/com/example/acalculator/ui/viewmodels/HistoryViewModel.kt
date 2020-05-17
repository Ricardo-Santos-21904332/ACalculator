package com.example.acalculator.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.acalculator.data.local.room.CalculatorDatabase
import com.example.acalculator.data.local.room.dao.OperationDao
import com.example.acalculator.data.remote.RetrofitBuilder
import com.example.acalculator.data.repositories.OperationRepository
import com.example.acalculator.domain.calculator.CalculatorLogic
import com.example.acalculator.entities.Operation
import com.example.acalculator.ui.listeners.OnListChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application)  {
    private val storage = CalculatorDatabase.getInstance(application).operationDao()
    private val operationRepository= OperationRepository(storage,RetrofitBuilder.getInstance(ENDPOINT))
    private val calculatorLogic = CalculatorLogic(operationRepository)

    fun registerListListener(listener: OnListChanged) {
        calculatorLogic.registerListener(listener)
        listener.onListChanged(calculatorLogic.getAll() as MutableList<Operation>)
    }

    fun unregisterListListener() {
        calculatorLogic.unregisterListener()
    }

    fun removerLista() {
        calculatorLogic.apagarDaLista()
    }
}