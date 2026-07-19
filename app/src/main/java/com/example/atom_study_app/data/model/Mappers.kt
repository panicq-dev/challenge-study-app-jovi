package com.example.atom_study_app.data.model

import com.example.atom_study_app.data.database.entity.ContentEntity
import com.example.atom_study_app.data.database.entity.FlashcardEntity
import com.example.atom_study_app.data.database.entity.SubjectEntity

fun SubjectEntity.toModel() = Subject(
    id = id,
    name = name,
    createdAt = createdAt
)

fun Subject.toEntity() = SubjectEntity(
    id = id,
    name = name,
    createdAt = createdAt
)

fun ContentEntity.toModel() = Content(
    id = id,
    subjectId = subjectId,
    title = title,
    imageUri = imageUri,
    createdAt = createdAt
)

fun Content.toEntity() = ContentEntity(
    id = id,
    subjectId = subjectId,
    title = title,
    imageUri = imageUri,
    createdAt = createdAt
)

fun FlashcardEntity.toModel() = Flashcard(
    frontDescription = frontDescription,
    backDescription = backDescription,
    id = id,
    subjectId = subjectId,
    contentId = contentId,
    createdAt = createdAt,
    reviewCount = reviewCount
)

fun Flashcard.toEntity() = FlashcardEntity(
    frontDescription = frontDescription,
    backDescription = backDescription,
    id = id,
    subjectId = subjectId,
    contentId = contentId,
    createdAt = createdAt,
    reviewCount = reviewCount
)