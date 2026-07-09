package com.example.atom_study_app


import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.atom_study_app.data.database.FlashcardDatabase
import com.example.atom_study_app.ui.library.flashcard.FlashcardScreen
import com.example.atom_study_app.ui.library.flashcard.FlashcardViewModel
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
                    ).build()
                }

                val viewModel: FlashcardViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return FlashcardViewModel(dao = db.dao) as T
                        }
                    }
                )

                val state by viewModel.state.collectAsState()

//                AppNavGraph()
                FlashcardScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

@Preview(name = "Modo Claro")
@Preview(name = "Modo Escuro", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FlashScreenPreview() {
    AtomstudyappTheme {
        // ContentScreen()
        //AppNavGraph()

    }
}







