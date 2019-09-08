package com.example.notificationsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notificationsapp.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Task::class), version = 1)
abstract class TaskDatabase : RoomDatabase() {

    // A base de dados contem a instanciaçãp do Dao
    abstract fun taskDao(): TaskDao

    //Instanciada como singleton
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {

            if (INSTANCE != null)
                return INSTANCE as TaskDatabase

            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()

                return INSTANCE as TaskDatabase
            }
        }

        //Acrescentado uma coroutine para permitir fazer override do onOpen e para eliminar
        // todos os dados e e rpopular a base de dados sempre que a aplicação inicia
        /*fun getDatabase(context: Context, scope: CoroutineScope): TaskDatabase {

            if (INSTANCE != null)
                return INSTANCE as TaskDatabase

            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).addCallback(TaskDatabaseCallback(scope)).build()

                return INSTANCE as TaskDatabase
            }
        }*/
    }

    //Criar callback para fazer override do onOpen

    private class TaskDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.taskDao())
                }
            }
        }

        suspend fun populateDatabase(taskDao: TaskDao) {

            taskDao.deleteAll()

            var task1 = Task(name = "Task 1", date = "11-12-2017", hour = "11.00")
            var task2 = Task(name = "Task 2", date = "11-12-2017", hour = "11.00", flow = true)
            var task3 = Task(name = "Task 3", date = "11-12-2017", hour = "11.00")
            var task4 = Task(name = "Task 4", date = "11-12-2017", hour = "11.00")
            var task5 = Task(name = "Task 5", date = "11-12-2017", hour = "11.00", flow = true)
            var task6 = Task(name = "Task 6", date = "11-12-2017", hour = "11.00")

            taskDao.insert(task1)
            taskDao.insert(task2)
            taskDao.insert(task3)
            taskDao.insert(task4)
            taskDao.insert(task5)
            taskDao.insert(task6)
            taskDao.insert(task1)
            taskDao.insert(task2)
            taskDao.insert(task3)
            taskDao.insert(task4)
            taskDao.insert(task5)
            taskDao.insert(task6)
        }
    }
}