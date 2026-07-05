package com.example.atom_study_app.ui.library

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.atom_study_app.data.model.Subject
import java.util.UUID

class LibraryViewModel : ViewModel() {


    val subjects = mutableStateListOf<Subject>()


    fun addSubject(name: String) {
        if (name.isNotBlank()) {
            subjects.add(Subject(id = UUID.randomUUID().toString(), name = name.trim()))
        }
    }
}