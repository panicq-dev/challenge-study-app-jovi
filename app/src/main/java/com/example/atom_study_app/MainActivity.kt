package com.example.atom_study_app


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.atom_study_app.nav.AppNavGraph
import com.example.atom_study_app.ui.theme.AtomstudyappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtomstudyappTheme() {
                AppNavGraph()
            }
        }
    }
}