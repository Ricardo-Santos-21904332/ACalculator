package com.example.acalculator.domain.calculator

import com.example.acalculator.entities.Operation
import com.example.acalculator.data.local.list.ListStorage
import com.example.acalculator.data.local.room.dao.OperationDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorLogic(private val storage: OperationDao) {

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

    fun performOperation(expression: String): Double {
        val expressionBuilder = ExpressionBuilder(expression).build()
        val result = expressionBuilder.evaluate()
        CoroutineScope(Dispatchers.IO).launch {
            storage.insert(
                Operation(
                    expression,
                    result
                )
            )
        }
        return result
    }

    fun apagarDaLista(posicao: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            //storage.deleteItem(posicao)
        }
    }

    fun getList(): MutableList<Operation> {
        var list = mutableListOf<Operation>()
        CoroutineScope(Dispatchers.IO).launch {
            list = storage.getAll() as MutableList<Operation>
        }
        return list
    }
}