package com.example.navigation.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navigation.data.db.TaskDao
import com.example.navigation.data.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}