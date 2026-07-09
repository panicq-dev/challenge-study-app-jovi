package com.example.atom_study_app.ui.library.flashcard

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.atom_study_app.R

@Composable
fun AddFlashCard(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var flashcardName by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.dialog_new_content_title)) },
        text = {
            OutlinedTextField(
                value = flashcardName,
                onValueChange = { flashcardName = it },
                label = { Text(stringResource(R.string.dialog_content_name_label)) },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(flashcardName)
                onDismiss()
            }) { Text(stringResource(R.string.dialog_confirm)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.dialog_cancel)) }
        }
    )
}