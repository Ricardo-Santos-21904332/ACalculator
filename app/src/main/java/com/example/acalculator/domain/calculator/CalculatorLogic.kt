package com.example.acalculator.domain.calculator

import com.example.acalculator.data.repositories.OperationRepository
import com.example.acalculator.entities.Operation
import com.example.acalculator.ui.listeners.OnListChanged

class CalculatorLogic(private val repository: OperationRepository) : OnListChanged{

    fun registerListener(listener: OnListChanged){
        repository.registerListener(listener)
    }
    
    fun unregisterListener(){
        repository.unregisterListener()
    }
    fun insertSymbol(display: String, symbol: String): String {
        return if (display == "0" && symbol != "C" && symbol != "<") symbol else if (symbol == "C") "0" else if (symbol == "<") apagarUltimo(
            display
        ) else display + symbol
    }

    fun apagarUltimo(display: String): String {
        if (display.isEmpty() || display.length == 1) {
            return "0"
        } else {
            return display.substring(0, display.length - 1)
        }
    }

    fun performOperationOnline(expression: String): Double {
        return repository.insertOnline(expression)
    }

    fun performOperationLocal(expression: String): Double {
        return repository.insertLocal(expression)
    }

    fun enviarOperacoesNaoEnviadas(){
        repository.enviarOperacoesNaoEnviadas()
    }

    fun apagarDaLista() {
        repository.delete()
    }

    fun apagarDaListaLocal() {
        repository.deleteLocal()
    }

    fun getAll() : List<Operation>? {
        return repository.getOperations()
    }

    override fun onListChanged(list: MutableList<Operation>?) {
        repository.onListChanged(list)
    }
}