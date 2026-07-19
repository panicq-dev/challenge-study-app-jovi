package com.example.atom_study_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contents")
data class Content(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectName: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
