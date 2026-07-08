package com.example.atom_study_app.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {

    // Dados que alteram, ou atualização
    var userProfile by mutableStateOf(
        UserProfile(
            name = "Roberto Dantas",
            email = "robertodantas990@gmail.com",
            streakDays = 5,
            points = 2550,
            dailyGoalProgress = 75,
            documentsCount = 48
        )
    )

    // Função para alterar dados (Editar Perfil) | nome
    private set
    fun updateName(newName: String, newEmail: String) {
        if (newName.isNotBlank()) {
            userProfile = userProfile.copy(name = newName)
            userProfile = userProfile.copy(email = newEmail)
        }
    }
}