package com.example.atom_study_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.atom_study_app.nav.Screen

import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(Screen.Home, Screen.Library, Screen.Account)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        items.forEach { screen ->
            val label = stringResource(screen.labelRes)
            val selected = currentRoute == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    val icon = when (screen) {
                        Screen.Home -> Icons.Default.Home
                        Screen.Library -> Icons.Default.List
                        Screen.Account -> Icons.Default.AccountCircle
                        else -> Icons.Default.List
                    }
                    Icon(
                        icon, 
                        contentDescription = label,
                        tint = if (selected) Color.White else Color.White.copy(alpha = 0.6f)
                    )
                },
                label = { 
                    Text(
                        label,
                        color = if (selected) Color.White else Color.White.copy(alpha = 0.6f)
                    ) 
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White.copy(alpha = 0.6f)
                )
            )
        }
    }
}