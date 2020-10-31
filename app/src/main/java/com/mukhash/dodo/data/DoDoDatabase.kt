package com.mukhash.dodo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mukhash.dodo.data.models.DoDoData

@Database(entities = [DoDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class DoDoDatabase : RoomDatabase() {

    abstract fun doDoDao(): DoDoDao

    companion object {
        @Volatile
        private var INSTANCE: DoDoDatabase? = null

        fun getDatabase(context: Context): DoDoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DoDoDatabase::class.java,
                    "dodo_database"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}