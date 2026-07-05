package com.example.atom_study_app.ui.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContentScreen(
    viewModel: ContentViewModel = viewModel(),
    subjectName: String,
    onBackClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var tabIndex by remember { mutableIntStateOf(0) }
    val titles = listOf("Resumos", "Exercícios", "Flashcards")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            // Botão de Voltar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = subjectName,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(25.dp)
                )
            }
            // Abas
            SecondaryTabRow(selectedTabIndex = tabIndex) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                when (index) {
                                    0 -> Icon(Icons.Default.LibraryBooks, contentDescription = null)
                                    1 -> Icon(Icons.Default.FolderOpen, contentDescription = null)
                                    2 -> Icon(Icons.Default.Task, contentDescription = null)
                                }
                                Text(text = title)
                            }
                        }
                    )
                }
            }
            // Conteúdo das abas.
            Box(modifier = Modifier.padding(16.dp)) {
                when (tabIndex) {
                    0 -> Text("Resumos.", color = MaterialTheme.colorScheme.onBackground)
                    1 -> Text("Exercícios", color = MaterialTheme.colorScheme.onBackground)
                    2 -> Text("Flashcards", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
        // Dialogo do botão +
        if (showDialog) {
            AddFlashCard(onDismiss = { showDialog = false }, onConfirm = { })
        }

        if (tabIndex == 2) {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        }
    }
}