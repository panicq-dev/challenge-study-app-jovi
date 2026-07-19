package com.example.atom_study_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atom_study_app.data.model.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(content: Content)

    @Delete
    suspend fun delete(content: Content)

    @Query("SELECT * FROM contents WHERE subjectName = :subjectName ORDER BY createdAt DESC")
    fun getContentsBySubject(subjectName: String): Flow<List<Content>>
}