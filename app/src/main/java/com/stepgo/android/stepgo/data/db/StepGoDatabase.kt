package com.stepgo.android.stepgo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stepgo.android.stepgo.data.entities.Step

@Database(entities = [Step::class], version = 9)
abstract class StepGoDatabase : RoomDatabase() {
    companion object {
        private const val dbName = "step_go.db"
        private lateinit var instance: StepGoDatabase

        fun getInstance(appContext: Context): StepGoDatabase {
            synchronized(StepGoDatabase::class) {
                instance = Room.databaseBuilder(appContext.applicationContext, StepGoDatabase::class.java, dbName)
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance
        }
    }

    abstract fun stepDao(): StepDao
}