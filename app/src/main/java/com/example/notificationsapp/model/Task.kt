package com.example.notificationsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
class Task(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var name: String = "",
    var date: String = "",
    var hour: String = "",
    var flow: Boolean = false
)