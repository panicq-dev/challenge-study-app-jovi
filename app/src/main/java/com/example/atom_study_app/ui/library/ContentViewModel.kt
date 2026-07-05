package com.example.atom_study_app.ui.library

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.atom_study_app.data.model.Content
import com.example.atom_study_app.data.model.Subject


class ContentViewModel : ViewModel() {
    val contents = mutableStateListOf<Content>()

    fun addContent(name:String, ){

    }
}
