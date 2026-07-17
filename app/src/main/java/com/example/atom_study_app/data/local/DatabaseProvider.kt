package com.example.atom_study_app.data.local

import android.content.Context
import androidx.room.Room
import com.example.atom_study_app.data.database.NoteZDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: NoteZDatabase? = null

    fun getDatabase(context: Context): NoteZDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteZDatabase::class.java,
                "notez_database"
            )
                .fallbackToDestructiveMigration(true) // fix pra erro de migração
                .build()
            INSTANCE = instance
            instance
        }
    }
}
