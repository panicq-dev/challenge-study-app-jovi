package com.example.atom_study_app.nav

import androidx.annotation.StringRes
import com.example.atom_study_app.R

// Rotas do app e o string.xml (res/values/strings.xml)
sealed class Screen(val route: String, @StringRes val labelRes: Int) {
    object Home : Screen("home", R.string.nav_home)
    object Library : Screen("library", R.string.nav_library)
    object Account : Screen("account", R.string.nav_account)
}