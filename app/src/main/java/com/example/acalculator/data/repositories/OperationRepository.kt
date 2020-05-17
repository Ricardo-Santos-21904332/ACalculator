package com.example.acalculator.data.repositories

import com.example.acalculator.data.local.room.dao.OperationDao
import com.example.acalculator.data.remote.services.OperationService
import com.example.acalculator.domain.auth.token
import com.example.acalculator.entities.Operation
import com.example.acalculator.ui.listeners.OnListChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import retrofit2.Retrofit

class OperationRepository(private val local: OperationDao, private val remote: Retrofit) :
    OnListChanged {

    private var listener: OnListChanged? = null
    override fun onListChanged(list: MutableList<Operation>?) {
        this.listener?.onListChanged(list)
    }

    fun registerListener(listener: OnListChanged) {
        this.listener = listener
        listener.onListChanged(getOperations())
    }

    fun unregisterListener() {
        this.listener = null
    }

    fun atualizarEcra(historic: MutableList<Operation>?) {
        CoroutineScope(Dispatchers.Main).launch {
            listener?.onListChanged(historic)
        }
    }


    fun getOperations(): MutableList<Operation> {
        var lista = mutableListOf<Operation>()
        CoroutineScope(Dispatchers.IO).launch {
            val service = remote.create(OperationService::class.java)
            val response = service.operationGet(token)
            if (response.isSuccessful) {
                local.delete()
                for (i in response.body()!!) {
                    i.enviadaParaServidor = true
                    local.insert(i)
                }
                atualizarEcra(response.body() as MutableList<Operation>)
            }
            lista = local.getAll() as MutableList<Operation>
        }
        return lista
    }

    fun insertOnline(expression: String): Double {
        val expressionBuilder = ExpressionBuilder(expression).build()
        val result = expressionBuilder.evaluate()
        val operation = Operation(expression, result, false)
        CoroutineScope(Dispatchers.IO).launch {
            val service = remote.create(OperationService::class.java)
            val response = service.operationPost(token, operation)
            if (response.isSuccessful) {
                operation.enviadaParaServidor = true
                local.insert(operation)
            }
        }
        return result
    }

    fun insertLocal(expression: String): Double {
        val expressionBuilder = ExpressionBuilder(expression).build()
        val result = expressionBuilder.evaluate()
        val operation = Operation(expression, result, false)
        CoroutineScope(Dispatchers.IO).launch {
            local.insert(operation)
        }
        return result
    }

    fun enviarOperacoesNaoEnviadas() {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in local.getAll()) {
                if (!i.enviadaParaServidor) {
                    val service = remote.create(OperationService::class.java)
                    service.operationPost(token, i)
                    i.enviadaParaServidor = true
                }
            }
            local.delete()
        }
    }

    fun delete() {
        CoroutineScope(Dispatchers.IO).launch {
            val service = remote.create(OperationService::class.java)
            val response = service.operationDelete(token)
            if (response.isSuccessful) {
                local.delete()
                atualizarEcra(getOperations())
            } else {
                local.delete()
            }
        }
    }

    fun deleteLocal() {
        CoroutineScope(Dispatchers.IO).launch {
            local.delete()
        }
    }

}