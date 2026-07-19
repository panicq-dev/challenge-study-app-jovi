package com.example.atom_study_app.data.model

data class Flashcard(
    val frontDescription: String,
    val backDescription: String,
    val id: Int = 0,
    val subjectId: Int? = null,
    val contentId: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val reviewCount: Int = 0
)