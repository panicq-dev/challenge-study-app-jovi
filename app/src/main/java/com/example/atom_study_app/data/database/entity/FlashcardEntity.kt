package com.example.atom_study_app.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = ContentEntity::class,
            parentColumns = ["id"],
            childColumns = ["contentId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("subjectId"), Index("contentId")]
)
data class FlashcardEntity(
    val frontDescription: String, // se o usuário quiser pesquisar pela pergunta
    val backDescription: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectId: Int? = null,
    val contentId: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val lastReviewedAt: Long? = null, // estatística
    val reviewCount: Int = 0 // estatística
)