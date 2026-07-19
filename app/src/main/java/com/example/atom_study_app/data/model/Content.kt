package com.example.atom_study_app.data.model

data class Content(
    val id: Int,
    val subjectId: Int,
    val title: String,
    val imageUri: String,
    val createdAt: Long
)