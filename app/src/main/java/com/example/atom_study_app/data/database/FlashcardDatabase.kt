package com.example.atom_study_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.atom_study_app.data.dao.FlashcardDao
import com.example.atom_study_app.data.model.Flashcard

import com.example.atom_study_app.data.dao.SubjectDao
import com.example.atom_study_app.data.model.Subject

import com.example.atom_study_app.data.dao.ContentDao
import com.example.atom_study_app.data.model.Content

import com.example.atom_study_app.data.dao.StatsDao
import com.example.atom_study_app.data.model.StudyStats

@Database(
    entities = [Flashcard::class, Subject::class, Content::class, StudyStats::class],
    version = 6
)
abstract class FlashcardDatabase : RoomDatabase() {
    abstract val dao: FlashcardDao
    abstract val subjectDao: SubjectDao
    abstract val contentDao: ContentDao
    abstract val statsDao: StatsDao

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