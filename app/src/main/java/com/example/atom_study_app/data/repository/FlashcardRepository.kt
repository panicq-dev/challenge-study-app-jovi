package com.example.atom_study_app.data.repository

import com.example.atom_study_app.data.dao.FlashcardDao
import com.example.atom_study_app.data.model.Flashcard
import com.example.atom_study_app.data.model.toEntity
import com.example.atom_study_app.data.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FlashcardRepository(private val dao: FlashcardDao) {

    fun getAllFlashcards(): Flow<List<Flashcard>> =
        dao.getAll().map { list -> list.map { it.toModel() } }

    suspend fun upsertFlashcard(flashcard: Flashcard) =
        dao.upsert(flashcard.toEntity())

    fun getFlashcardsBySubject(subjectId: Int): Flow<List<Flashcard>> =
        dao.getBySubject(subjectId).map { list -> list.map { it.toModel() } }

    fun getFlashcardsByContent(contentId: Int): Flow<List<Flashcard>> =
        dao.getByContent(contentId).map { list -> list.map { it.toModel() } }

    suspend fun addFlashcard(flashcard: Flashcard): Long =
        dao.insert(flashcard.toEntity())

    suspend fun updateFlashcard(flashcard: Flashcard) =
        dao.update(flashcard.toEntity())

    suspend fun deleteFlashcard(flashcard: Flashcard) =
        dao.delete(flashcard.toEntity())
}