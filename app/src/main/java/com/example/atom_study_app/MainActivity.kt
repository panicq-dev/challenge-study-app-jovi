package com.example.atom_study_app


import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.atom_study_app.nav.AppNavGraph
import com.example.atom_study_app.ui.library.Flashcard
import com.example.atom_study_app.ui.theme.AtomstudyappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtomstudyappTheme() {
                //AppNavGraph()
                FlashScreenPreview()
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
        Flashcard()
    }
}







