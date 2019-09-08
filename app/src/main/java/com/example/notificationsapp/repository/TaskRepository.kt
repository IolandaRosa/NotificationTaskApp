package com.example.notificationsapp.repository

import androidx.lifecycle.LiveData
import com.example.notificationsapp.db.TaskDao
import com.example.notificationsapp.model.Task

//Reponsavel por fazer os acessos à BD ou a uma API caso exista
class TaskRepository(private val taskDao: TaskDao) {

    //Como foi usada a classe LiveData então o Room vai executar numa tarefa à parte
    //Vai existir um observer que vai notificar a atividade quando algo muda
    val allTasks: LiveData<MutableList<Task>> = taskDao.getAll()

    //suspend indica que a função tem de ser chamada de uma courotina ou outra função suspensa
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    fun delete(task: Task) {
        taskDao.delete(task.id!!)
    }
}