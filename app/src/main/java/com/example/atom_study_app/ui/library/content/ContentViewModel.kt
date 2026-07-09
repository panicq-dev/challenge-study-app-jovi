package com.example.atom_study_app.ui.library.content

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.atom_study_app.data.model.Content

class ContentViewModel : ViewModel() {
    val contents = mutableStateListOf<Content>()

    fun addContent(name:String, ){

    }
}