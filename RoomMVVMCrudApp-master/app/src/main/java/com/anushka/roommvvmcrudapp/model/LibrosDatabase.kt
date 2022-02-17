package com.anushka.roommvvmcrudapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LibrosDataClass::class], version = 1)
abstract class LibrosDatabase : RoomDatabase() {
    abstract val librosDAO: LibrosDAO

    companion object {
        @Volatile
        private var INSTANCE: LibrosDatabase? = null
        fun getInstance(context: Context): LibrosDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LibrosDatabase::class.java,
                        "libros"
                    ).build()
                }
                return instance
            }
        }
    }
}


