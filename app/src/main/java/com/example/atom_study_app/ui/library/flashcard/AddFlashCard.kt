package com.example.atom_study_app.ui.library.flashcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.atom_study_app.R

@Composable
fun AddFlashCard(
    state: FlashcardState,
    onEvent: (FlashcardEvent) -> Unit,
    subjectName: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.dialog_new_content_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = state.frontDescription,
                    onValueChange = { onEvent(FlashcardEvent.SetFrontDescription(it)) },
                    label = { Text("Frente") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = state.backDescription,
                    onValueChange = { onEvent(FlashcardEvent.SetBackDescription(it)) },
                    label = { Text("Verso") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onEvent(FlashcardEvent.SaveFlashcard(subjectName))
                onDismiss()
            }) { Text(stringResource(R.string.dialog_confirm)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.dialog_cancel)) }
        }
    )
}