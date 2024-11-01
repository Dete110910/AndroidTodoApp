package com.example.navigation.di

import android.content.Context
import androidx.room.Room
import com.example.navigation.data.AppDatabase
import com.example.navigation.data.db.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tasks_db"
        ).build()

    @Provides
    @Singleton
    fun providesTaskDao(appDatabase: AppDatabase) : TaskDao =
        appDatabase.taskDao()

}