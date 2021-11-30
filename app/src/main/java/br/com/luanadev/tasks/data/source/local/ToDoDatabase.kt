package br.com.luanadev.tasks.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.luanadev.tasks.data.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDao
}