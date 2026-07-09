package com.example.atom_study_app.ui.library.flashcard

import com.example.atom_study_app.data.model.Flashcard

data class FlashcardState( // Variáveis que mudam.
    val flashcards: List<Flashcard> = emptyList(),
    val frontDescription: String = "",
    val backDescription: String = "",
    val isAddingFlashcard: Boolean = false

)