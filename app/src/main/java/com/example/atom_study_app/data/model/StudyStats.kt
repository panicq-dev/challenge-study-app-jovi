package com.example.atom_study_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_stats")
data class StudyStats(
    @PrimaryKey val date: Long,
    val totalCorrect: Int = 0,
    val totalWrong: Int = 0,
    val subjectsStudied: Int = 0
)