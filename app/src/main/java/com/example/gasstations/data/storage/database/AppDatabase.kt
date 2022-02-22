package com.example.gasstations.data.storage.database

import android.content.Context
import androidx.room.*
import com.example.gasstations.data.storage.models.RefuelCache

@Database(entities = [RefuelCache::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun refuelDao(): RefuelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "refuelsDb"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}