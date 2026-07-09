package com.example.atom_study_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.atom_study_app.data.dao.FlashcardDao
import com.example.atom_study_app.data.model.Flashcard

@Database(
    entities = [Flashcard::class],
    version = 1
)
abstract class FlashcardDatabase : RoomDatabase() {
    abstract val dao: FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: FlashcardDatabase? = null

        fun getInstance(context: Context): FlashcardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlashcardDatabase::class.java,
                    "flashcard_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}