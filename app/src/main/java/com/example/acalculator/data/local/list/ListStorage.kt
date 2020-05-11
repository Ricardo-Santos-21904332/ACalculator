package com.example.acalculator.data.local.list

import com.example.acalculator.entities.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListStorage private constructor() {

    private val storage = mutableListOf<Operation>()

    companion object {
        private var instance: ListStorage? = null

        fun getInstance(): ListStorage {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        ListStorage()
                }
                return instance as ListStorage
            }
        }
    }

    suspend fun insert(operation: Operation) {
        withContext(Dispatchers.IO) {
            Thread.sleep(30000)
            storage.add(operation)
        }
    }

    suspend fun getAll(): MutableList<Operation> {
       return withContext(Dispatchers.IO) {
            Thread.sleep(30000)
            storage
        }
    }

    suspend fun deleteItem(posicao: Int) {
        withContext(Dispatchers.IO) {
            Thread.sleep(30000)
            storage.removeAt(posicao)
        }
    }
}