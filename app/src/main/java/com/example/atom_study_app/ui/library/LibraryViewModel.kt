package com.example.atom_study_app.ui.library

import androidx.lifecycle.ViewModel
import com.example.atom_study_app.data.model.Subject

import androidx.lifecycle.viewModelScope
import com.example.atom_study_app.data.dao.SubjectDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import com.example.atom_study_app.data.dao.ContentDao
import com.example.atom_study_app.data.model.Content

class LibraryViewModel(
    private val subjectDao: SubjectDao,
    private val contentDao: ContentDao
) : ViewModel() {

    val subjects: StateFlow<List<Subject>> = subjectDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getContents(subjectName: String): Flow<List<Content>> {
        return contentDao.getContentsBySubject(subjectName)
    }

    fun addSubject(name: String) {
        if (name.isNotBlank()) {
            viewModelScope.launch {
                subjectDao.insert(Subject(name = name.trim()))
            }
        }
    }

    fun addContent(subjectName: String, contentName: String) {
        if (contentName.isNotBlank()) {
            viewModelScope.launch {
                contentDao.insert(Content(subjectName = subjectName, name = contentName.trim()))
            }
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            subjectDao.delete(subject)
        }
    }

    fun deleteContent(content: Content) {
        viewModelScope.launch {
            contentDao.delete(content)
        }
    }
}