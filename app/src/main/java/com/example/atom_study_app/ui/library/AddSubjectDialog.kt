package com.example.atom_study_app.ui.library

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.example.atom_study_app.R

// Tudo isso é o POPUP da Matéria, ao clicar em adicionar.
@Composable
fun AddSubjectDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var subjectName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.dialog_new_subject_title)) },
        text = {
            OutlinedTextField(
                value = subjectName,
                onValueChange = { subjectName = it },
                label = { Text(stringResource(R.string.dialog_subject_name_label)) },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(subjectName)
                onDismiss()
            }) { Text(stringResource(R.string.dialog_confirm)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.dialog_cancel)) }
        }
    )
}