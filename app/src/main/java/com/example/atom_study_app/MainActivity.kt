package com.example.atom_study_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.atom_study_app.data.database.FlashcardDatabase
import com.example.atom_study_app.ui.theme.AtomstudyappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtomstudyappTheme {
                val db = remember {
                    Room.databaseBuilder(
                        applicationContext,
                        FlashcardDatabase::class.java,
                        "atom_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                com.example.atom_study_app.nav.AppNavGraph(database = db)
            }
        }
    }
}

@Preview(name = "Modo Claro")
@Preview(name = "Modo Escuro", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FlashScreenPreview() {
    AtomstudyappTheme {

    }
}







