package com.example.atom_study_app.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "contents",
    foreignKeys = [ ForeignKey(entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE) ],
    indices = [Index("subjectId")]
)


data class ContentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectId: Int,
    val title: String,
    val imageUri: String,
    val createdAt: Long = System.currentTimeMillis()
)
