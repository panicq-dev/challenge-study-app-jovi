package com.example.atom_study_app.data.repository

import com.example.atom_study_app.data.dao.ContentDao
import com.example.atom_study_app.data.model.Content
import com.example.atom_study_app.data.model.toEntity
import com.example.atom_study_app.data.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContentRepository(private val dao: ContentDao) {

    fun getContentsBySubject(subjectId: Int): Flow<List<Content>> =
        dao.getBySubject(subjectId).map { list -> list.map { it.toModel() } }

    suspend fun getContentById(id: Int): Content? =
        dao.getById(id)?.toModel()

    suspend fun addContent(content: Content): Long =
        dao.insert(content.toEntity())

    suspend fun updateContent(content: Content) =
        dao.update(content.toEntity())

    suspend fun deleteContent(content: Content) =
        dao.delete(content.toEntity())
}