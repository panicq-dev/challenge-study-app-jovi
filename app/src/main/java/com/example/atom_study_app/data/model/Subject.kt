package com.example.atom_study_app.data.model
// Kaue.
// Fala, super tralalelito Kaue-Chan. Aqui é uma model.
// Imagino que já saiba, mas é o modelo do que é uma "Matéria" ou "Subject", como preferir.
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
