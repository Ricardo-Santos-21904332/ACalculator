package com.example.acalculator.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.acalculator.entities.Operation

@Dao
interface OperationDao {

    @Insert
    suspend fun insert(operation : Operation)

    @Query("SELECT * FROM operation")
    suspend fun getAll(): List<Operation>

    @Query("SELECT * FROM operation WHERE uuid = :uuid")
    suspend fun getById(uuid : String): Operation

    @Query("DELETE FROM operation")
    suspend fun delete()
}