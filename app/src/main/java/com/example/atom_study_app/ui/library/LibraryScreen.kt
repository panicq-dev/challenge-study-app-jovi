package com.example.atom_study_app.ui.library

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atom_study_app.R
import com.example.atom_study_app.data.model.Subject
import com.example.atom_study_app.data.model.Content

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel,
    onContentClick: (String, String) -> Unit
) {
    val subjects by viewModel.subjects.collectAsState()
    var showSubjectDialog by remember { mutableStateOf(false) }
    var expandedSubject by remember { mutableStateOf<String?>(null) }
    var subjectToAddContent by remember { mutableStateOf<String?>(null) }
    
    var searchQuery by remember { mutableStateOf("") }
    
    var itemToDelete by remember { mutableStateOf<Any?>(null) }

    val filteredSubjects = if (searchQuery.isBlank()) subjects else {
        subjects.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Minha Biblioteca",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(16.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Pesquisar matéria...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            if (filteredSubjects.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (searchQuery.isBlank()) stringResource(R.string.library_empty) else "Nenhuma matéria encontrada.",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredSubjects) { subject ->
                        SubjectCard(
                            subject = subject,
                            isExpanded = expandedSubject == subject.name,
                            onExpandClick = {
                                expandedSubject = if (expandedSubject == subject.name) null else subject.name
                            },
                            onAddContentClick = { subjectToAddContent = subject.name },
                            contentsFlow = viewModel.getContents(subject.name),
                            onContentClick = { content -> onContentClick(subject.name, content.name) },
                            onDeleteSubject = { itemToDelete = subject },
                            onDeleteContent = { itemToDelete = it }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showSubjectDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar Matéria")
        }
    }

    if (showSubjectDialog) {
        AddSubjectDialog(
            onDismiss = { showSubjectDialog = false },
            onConfirm = { name -> viewModel.addSubject(name) }
        )
    }

    subjectToAddContent?.let { subjectName ->
        AddContentDialog(
            subjectName = subjectName,
            onDismiss = { subjectToAddContent = null },
            onConfirm = { contentName ->
                viewModel.addContent(subjectName, contentName)
            }
        )
    }

    itemToDelete?.let { item ->
        AlertDialog(
            onDismissRequest = { itemToDelete = null },
            title = { Text("Excluir ${if (item is Subject) "Matéria" else "Conteúdo"}?") },
            text = { Text("Esta ação não pode ser desfeita.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (item is Subject) viewModel.deleteSubject(item)
                        else if (item is Content) viewModel.deleteContent(item)
                        itemToDelete = null
                    }
                ) {
                    Text("Excluir", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun SubjectCard(
    subject: Subject,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    onAddContentClick: () -> Unit,
    contentsFlow: kotlinx.coroutines.flow.Flow<List<Content>>,
    onContentClick: (Content) -> Unit,
    onDeleteSubject: () -> Unit,
    onDeleteContent: (Content) -> Unit
) {
    val contents by contentsFlow.collectAsState(initial = emptyList())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandClick() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = subject.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onAddContentClick) {
                        Icon(Icons.Default.AddCircleOutline, contentDescription = "Novo Conteúdo", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onDeleteSubject) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "Excluir Matéria", tint = Color.Gray.copy(alpha = 0.6f))
                    }
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    if (contents.isEmpty()) {
                        Text(
                            "Nenhum conteúdo adicionado.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        contents.forEach { content ->
                            ContentItem(
                                content = content, 
                                onClick = { onContentClick(content) },
                                onDelete = { onDeleteContent(content) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContentItem(content: Content, onClick: () -> Unit, onDelete: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                    Icons.Default.Description,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = content.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.DeleteOutline, contentDescription = "Excluir Conteúdo", tint = Color.Gray.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun AddContentDialog(
    subjectName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var contentName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Novo Conteúdo em $subjectName") },
        text = {
            OutlinedTextField(
                value = contentName,
                onValueChange = { contentName = it },
                label = { Text("Nome do Conteúdo") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(contentName)
                onDismiss()
            }) { Text("Adicionar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}