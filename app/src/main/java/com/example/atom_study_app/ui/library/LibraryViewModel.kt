package com.example.atom_study_app.ui.library

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.atom_study_app.data.model.Subject
import java.util.UUID

class LibraryViewModel : ViewModel() {

    // Aqui entra o conceito de Recomposição, que devem dar uma olhada lá na documentação.
    val subjects = mutableStateListOf<Subject>()

    // Adiciona uma nova matéria (if isNotBlank). Conceito de recomposição.
    fun addSubject(name: String) {
        if (name.isNotBlank()) {
            subjects.add(Subject(id = UUID.randomUUID().toString(), name = name.trim()))
        }
    }
}