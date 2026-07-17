package com.example.atom_study_app.data.repository

import com.example.atom_study_app.data.dao.SubjectDao
import com.example.atom_study_app.data.model.toEntity
import com.example.atom_study_app.data.model.toModel
import com.example.atom_study_app.data.model.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepository(private val dao: SubjectDao) {

    fun getAllSubjects(): Flow<List<Subject>> =
        dao.getAll().map { list -> list.map { it.toModel() } }

    suspend fun getSubjectById(id: Int): Subject? =
        dao.getById(id)?.toModel()

    suspend fun addSubject(subject: Subject): Long =
        dao.insert(subject.toEntity())

    suspend fun updateSubject(subject: Subject) =
        dao.update(subject.toEntity())

    suspend fun deleteSubject(subject: Subject) =
        dao.delete(subject.toEntity())
}
