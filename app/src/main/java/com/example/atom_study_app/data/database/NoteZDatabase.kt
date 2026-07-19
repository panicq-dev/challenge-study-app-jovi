package com.example.atom_study_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atom_study_app.data.dao.ContentDao
import com.example.atom_study_app.data.dao.FlashcardDao
import com.example.atom_study_app.data.dao.SubjectDao
import com.example.atom_study_app.data.database.entity.ContentEntity
import com.example.atom_study_app.data.database.entity.FlashcardEntity
import com.example.atom_study_app.data.database.entity.SubjectEntity

@Database(
    entities = [SubjectEntity::class, ContentEntity::class, FlashcardEntity::class],
    version = 2,
    exportSchema = false
)
abstract class NoteZDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun contentDao(): ContentDao
    abstract fun flashcardDao(): FlashcardDao

}
