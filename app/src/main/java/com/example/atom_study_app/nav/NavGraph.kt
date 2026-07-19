package com.example.atom_study_app.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atom_study_app.ui.components.BottomNavBar

import com.example.atom_study_app.ui.account.AccountScreen
import com.example.atom_study_app.ui.home.HomeScreen
import com.example.atom_study_app.ui.library.LibraryScreen
import com.example.atom_study_app.ui.library.content.ContentScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atom_study_app.data.database.NoteZDatabase
import com.example.atom_study_app.ui.library.LibraryViewModel

// Babysharks, tomem cuidado ao mexer aqui. Aqui define alguns elementos fixos, como a Nav-Bar
// e a rotação de tela.
@Composable
fun AppNavGraph(database: NoteZDatabase) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Library.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Library.route) {
                val libraryViewModel: LibraryViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return LibraryViewModel(database.subjectDao()) as T
                        }
                    }
                )
                LibraryScreen(
                    viewModel = libraryViewModel,
                    onSubjectClick = { name ->
                        navController.navigate(Screen.SubjectDetail.createRoute(name))
                    }
                )
            }
            composable(Screen.Account.route) { AccountScreen() }
            composable(Screen.SubjectDetail.route) { backStackEntry ->
                val subjectName = backStackEntry.arguments?.getString("subjectName") ?: ""
                ContentScreen(
                    subjectName = subjectName,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}