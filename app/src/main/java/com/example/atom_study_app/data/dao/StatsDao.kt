package com.example.atom_study_app.data.dao

import androidx.room.*
import com.example.atom_study_app.data.model.StudyStats
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(stats: StudyStats)

    @Query("SELECT * FROM study_stats WHERE date = :date")
    suspend fun getStatsForDate(date: Long): StudyStats?

    @Query("SELECT * FROM study_stats ORDER BY date DESC LIMIT 7")
    fun getLastWeekStats(): Flow<List<StudyStats>>

    @Query("SELECT SUM(totalCorrect) FROM study_stats")
    fun getTotalCorrect(): Flow<Int?>
}