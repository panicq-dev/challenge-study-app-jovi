package com.example.atom_study_app.data.model

data class Content(
    val id: Int,
    val subject_id: Int,
    val name: String,
    var description: String,
    val isCompleted: Boolean = false,
    val timeStamp: Long = System.currentTimeMillis()
)
