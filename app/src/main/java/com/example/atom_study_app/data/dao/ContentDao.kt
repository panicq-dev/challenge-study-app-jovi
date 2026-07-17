package com.example.atom_study_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.atom_study_app.data.database.entity.ContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // se houver algum conflito, o aplicativo substitui
    suspend fun insert(content: ContentEntity): Long

    @Update
    suspend fun update(content: ContentEntity)

    @Delete
    suspend fun delete(content: ContentEntity)

    @Query("SELECT * FROM contents WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getBySubject(subjectId: Int): Flow<List<ContentEntity>>

    @Query("SELECT * FROM contents WHERE id = :id")
    suspend fun getById(id: Int): ContentEntity?
}
