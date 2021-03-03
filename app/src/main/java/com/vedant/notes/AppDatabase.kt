package com.vedant.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vedant.notes.models.Notes
import com.vedant.notes.ui.DB_NAME


@Database(entities = [Notes::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            kotlin.synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}