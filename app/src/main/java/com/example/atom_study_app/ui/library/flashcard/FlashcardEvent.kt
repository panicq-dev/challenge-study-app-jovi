package com.example.atom_study_app.ui.library.flashcard

import com.example.atom_study_app.data.model.Flashcard

sealed interface FlashcardEvent { // Cada ação do usuário, é um evento. Eventos que não precisam de parâmetros, instanciamos com object. Os demais, não podemos instanciar para não ocupar memória demais
    data class SetFrontDescription(val value: String) : FlashcardEvent

    data class SetBackDescription(val value: String) : FlashcardEvent


    object SaveFlashcard : FlashcardEvent

    data class DeleteFlashcard(val flashcard: Flashcard) : FlashcardEvent
}