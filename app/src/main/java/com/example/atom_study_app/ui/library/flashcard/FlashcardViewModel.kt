package com.example.atom_study_app.ui.library.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atom_study_app.data.dao.FlashcardDao
import com.example.atom_study_app.data.database.entity.FlashcardEntity
import com.example.atom_study_app.data.model.Flashcard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashcardViewModel(private val dao: FlashcardDao) : ViewModel() {
    private val _state = MutableStateFlow(FlashcardState())

    val state: StateFlow<FlashcardState> = combine(
        _state,
        dao.getAll().map { entities -> entities.map { it.toFlashcard() } }
    ) { state, flashcards ->
        state.copy(flashcards = flashcards)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FlashcardState())

    fun onEvent(event: FlashcardEvent) {
        when (event) {

            is FlashcardEvent.SetFrontDescription -> {
                _state.update {
                    it.copy(frontDescription = event.value)
                }
            }

            is FlashcardEvent.SetBackDescription -> {
                _state.update {
                    it.copy(backDescription = event.value)
                }
            }

            FlashcardEvent.SaveFlashcard -> {
                val state = _state.value

                val flashcard = Flashcard(
                    frontDescription = state.frontDescription,
                    backDescription = state.backDescription
                )

                viewModelScope.launch {
                    dao.upsert(flashcard.toEntity())
                }

                _state.update {
                    it.copy(frontDescription = "", backDescription = "")
                }
            }

            is FlashcardEvent.DeleteFlashcard -> {
                viewModelScope.launch {
                    dao.delete(event.flashcard.toEntity())
                }
            }
        }
    }

    // Mapper.

    private fun FlashcardEntity.toFlashcard(): Flashcard {
        return Flashcard(
            id = this.id,
            frontDescription = this.frontDescription,
            backDescription = this.backDescription
        )
    }

    private fun Flashcard.toEntity(): FlashcardEntity {
        return FlashcardEntity(
            id = this.id,
            frontDescription = this.frontDescription,
            backDescription = this.backDescription
        )
    }
}