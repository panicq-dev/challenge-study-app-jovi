package com.example.atom_study_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.atom_study_app.data.model.Flashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Upsert
    suspend fun upsertFlashcard(flashcard: Flashcard)
    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)
    @Query("SELECT * FROM flashcards WHERE subjectName = :subjectName")
    fun getFlashcardsBySubject(subjectName: String): Flow<List<Flashcard>>
}