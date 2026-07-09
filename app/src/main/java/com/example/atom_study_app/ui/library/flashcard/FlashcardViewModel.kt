package com.example.atom_study_app.ui.library.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atom_study_app.data.dao.FlashcardDao
import com.example.atom_study_app.data.model.Flashcard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashcardViewModel(private val dao: FlashcardDao) : ViewModel() {
    private val _state = MutableStateFlow(FlashcardState())
    val state: StateFlow<FlashcardState> = combine(
        _state,
        dao.getAllFlashcards()
    ) { state, flashcards ->
        state.copy(flashcards = flashcards)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FlashcardState())
    fun onEvent (event: FlashcardEvent){
        when(event){

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
                    dao.upsertFlashcard(flashcard)
                }
            }

            is FlashcardEvent.DeleteFlashcard -> {
                viewModelScope.launch {
                    dao.deleteFlashcard(event.flashcard)
                }
            }
        }
}}