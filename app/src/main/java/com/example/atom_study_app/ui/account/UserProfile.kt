package com.example.atom_study_app.ui.account

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val streakDays: Int = 0,
    val points: Int = 0,
    val dailyGoalProgress: Int = 0,
    val documentsCount: Int = 0
)