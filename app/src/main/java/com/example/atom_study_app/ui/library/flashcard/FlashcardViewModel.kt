package com.example.atom_study_app.ui.library.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atom_study_app.data.dao.FlashcardDao
import com.example.atom_study_app.data.model.Flashcard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.example.atom_study_app.data.dao.StatsDao
import com.example.atom_study_app.data.model.StudyStats
import java.util.Calendar

class FlashcardViewModel(
    private val dao: FlashcardDao,
    private val statsDao: StatsDao
) : ViewModel() {
    private val _state = MutableStateFlow(FlashcardState())
    val state = _state.asStateFlow()

    fun getFlashcards(subjectName: String): Flow<List<Flashcard>> {
        return dao.getFlashcardsBySubject(subjectName)
    }

    fun onEvent(event: FlashcardEvent) {
        when (event) {
            is FlashcardEvent.SetFrontDescription -> {
                _state.update { it.copy(frontDescription = event.value) }
            }
            is FlashcardEvent.SetBackDescription -> {
                _state.update { it.copy(backDescription = event.value) }
            }
            is FlashcardEvent.SaveFlashcard -> {
                val currentState = _state.value
                val flashcard = Flashcard(
                    subjectName = event.subjectName,
                    frontDescription = currentState.frontDescription,
                    backDescription = currentState.backDescription
                )
                viewModelScope.launch {
                    dao.upsertFlashcard(flashcard)
                    _state.update { it.copy(frontDescription = "", backDescription = "") }
                }
            }
            is FlashcardEvent.DeleteFlashcard -> {
                viewModelScope.launch {
                    dao.deleteFlashcard(event.flashcard)
                }
            }
            is FlashcardEvent.UpdateScore -> {
                viewModelScope.launch {
                    val updatedFlashcard = if (event.isCorrect) {
                        event.flashcard.copy(
                            correctCount = event.flashcard.correctCount + 1,
                            lastReviewed = System.currentTimeMillis()
                        )
                    } else {
                        event.flashcard.copy(
                            wrongCount = event.flashcard.wrongCount + 1,
                            lastReviewed = System.currentTimeMillis()
                        )
                    }
                    dao.upsertFlashcard(updatedFlashcard)

                    val today = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.timeInMillis

                    val currentStats = statsDao.getStatsForDate(today) ?: StudyStats(today)
                    val newStats = if (event.isCorrect) {
                        currentStats.copy(totalCorrect = currentStats.totalCorrect + 1)
                    } else {
                        currentStats.copy(totalWrong = currentStats.totalWrong + 1)
                    }
                    statsDao.insertOrUpdate(newStats)
                }
            }
        }
    }
}