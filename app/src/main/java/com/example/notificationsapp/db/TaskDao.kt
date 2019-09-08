package com.example.notificationsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.notificationsapp.model.Task

//Operações de acesso a base de dados
@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    fun getAll(): LiveData<MutableList<Task>>

    @Insert(onConflict = IGNORE)
    fun insert(task: Task): Long

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("DELETE FROM task_table WHERE id=:id")
    fun delete(id: Long)
}