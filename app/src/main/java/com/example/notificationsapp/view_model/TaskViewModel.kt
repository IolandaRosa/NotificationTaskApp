package com.example.notificationsapp.view_model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notificationsapp.db.TaskDatabase
import com.example.notificationsapp.handlers.NotificationHandler
import com.example.notificationsapp.model.Task
import com.example.notificationsapp.repository.TaskRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class TaskViewModel(application: Application) : AndroidViewModel(application), Serializable {

    // O view model tem uma referencia para o repositorio
    private val repository: TaskRepository

    // Tam a variavel do tipo LiveData para aterar a lista quando esta é alterada
    val allTasks: LiveData<MutableList<Task>>

    //Para iniciar as variaveis
    init {
        //Vai instanciar a base de dados para conseguir a referencia para o dao para instanciar o repositorio
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        //val taskDao = TaskDatabase.getDatabase(application, viewModelScope).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    // Lançar uma courotina para inserir pois nao pode ser executado da mainThread
    //O viewModelScope é baseado no lifecicle do view model e é especifico para kotlin
    //com a dependencia lifecycle-viewmodel-ktx
    fun insert(task: Task) = GlobalScope.launch {
        repository.insert(task)
    }

    fun createChannel(context: Context) {

        val notificationHandler = NotificationHandler(context)
        notificationHandler.createNotificationChannel()
    }

    fun delete(task:Task) = GlobalScope.launch {
        repository.delete(task)
    }
}