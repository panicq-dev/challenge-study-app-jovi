package com.example.atom_study_app.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atom_study_app.data.dao.SubjectDao
import com.example.atom_study_app.data.database.entity.SubjectEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(private val dao: SubjectDao) : ViewModel() {

    val subjects: StateFlow<List<SubjectEntity>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addSubject(name: String) {
        if (name.isNotBlank()) {
            viewModelScope.launch {
                dao.insert(
                    SubjectEntity(
                        name = name.trim()
                    )
                )
            }
        }
    }

    fun deleteSubject(subject: SubjectEntity) {
        viewModelScope.launch {
            dao.delete(subject)
        }
    }
}