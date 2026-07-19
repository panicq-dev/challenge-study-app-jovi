package com.example.atom_study_app.ui.library.content

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atom_study_app.data.model.Flashcard
import com.example.atom_study_app.ui.library.flashcard.FlashcardEvent
import com.example.atom_study_app.ui.library.flashcard.FlashcardScreen
import com.example.atom_study_app.ui.library.flashcard.FlashcardViewModel
import com.example.atom_study_app.ui.theme.RoyalBlue
import com.example.atom_study_app.ui.theme.RoyalBlueDark

@Composable
fun ContentScreen(
    flashcardViewModel: FlashcardViewModel,
    subjectName: String,
    contentName: String,
    onBackClick: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var tabIndex by remember { mutableIntStateOf(0) }
    val titles = listOf("Flashcard", "Exercícios", "Resumos")
    
    var searchQuery by remember { mutableStateOf("") }
    var flashcardToDelete by remember { mutableStateOf<Flashcard?>(null) }
    
    val flashcardState by flashcardViewModel.state.collectAsState()
    val flashcards by flashcardViewModel.getFlashcards("$subjectName/$contentName").collectAsState(initial = emptyList())

    val filteredFlashcards = if (searchQuery.isBlank()) flashcards else {
        flashcards.filter { it.frontDescription.contains(searchQuery, ignoreCase = true) }
    }

    val selectedFlashcard = if (selectedIndex >= 0 && selectedIndex < filteredFlashcards.size) filteredFlashcards[selectedIndex] else null

    val gradient = Brush.verticalGradient(
        colors = listOf(RoyalBlue, RoyalBlueDark)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradient)
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 0.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                        }
                        Row {
                            IconButton(onClick = { }) {
                                Icon(Icons.Outlined.Share, contentDescription = "Compartilhar", tint = Color.White)
                            }
                            IconButton(onClick = { }) {
                                Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Salvar", tint = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = subjectName,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Conteúdo de $contentName",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        titles.forEachIndexed { index, title ->
                            Column(
                                modifier = Modifier.clickable { tabIndex = index },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = title,
                                    color = if (tabIndex == index) Color.White else Color.White.copy(alpha = 0.5f),
                                    fontWeight = if (tabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                                AnimatedVisibility(
                                    visible = tabIndex == index,
                                    enter = expandHorizontally() + fadeIn(),
                                    exit = shrinkHorizontally() + fadeOut()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .width(40.dp)
                                            .height(3.dp)
                                            .background(Color.White, RoundedCornerShape(2.dp))
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (searchQuery.isEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.7f))
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Pesquisar",
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 16.sp),
                                cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.White),
                                singleLine = true
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                AnimatedContent(
                    targetState = tabIndex,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                        } else {
                            slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                        }.using(SizeTransform(clip = false))
                    },
                    label = "tabTransition"
                ) { targetTabIndex ->
                    when (targetTabIndex) {
                        0 -> {
                            if (filteredFlashcards.isEmpty()) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (searchQuery.isEmpty()) "Nenhum flashcard ainda." else "Nenhum flashcard encontrado.",
                                        color = Color.Gray
                                    )
                                }
                            } else {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(filteredFlashcards) { flashcard ->
                                        ModernFlashcardItem(
                                            flashcard = flashcard,
                                            onClick = { selectedIndex = filteredFlashcards.indexOf(flashcard) },
                                            onDelete = { flashcardToDelete = flashcard }
                                        )
                                    }
                                }
                            }
                        }
                        1 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Exercícios em breve.", color = Color.Gray)
                        }
                        2 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Resumos em breve.", color = Color.Gray)
                        }
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = tabIndex == 0 && !showAddDialog && selectedIndex == -1,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut(),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    FloatingActionButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier.padding(bottom = 16.dp),
                        containerColor = RoyalBlue,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar")
                    }
                }
            }
        }

        androidx.compose.animation.AnimatedVisibility(
            visible = showAddDialog,
            enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it },
            exit = fadeOut(tween(400)) + slideOutVertically(tween(400)) { it }
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                FlashcardScreen(
                    state = flashcardState,
                    onEvent = flashcardViewModel::onEvent,
                    subjectName = subjectName,
                    contentName = contentName,
                    onDismiss = { showAddDialog = false }
                )
            }
        }

        androidx.compose.animation.AnimatedVisibility(
            visible = selectedFlashcard != null,
            enter = fadeIn(tween(400)) + scaleIn(tween(400)),
            exit = fadeOut(tween(400)) + scaleOut(tween(400))
        ) {
            selectedFlashcard?.let { flashcard ->
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    FlashcardScreen(
                        state = flashcardState,
                        onEvent = flashcardViewModel::onEvent,
                        subjectName = subjectName,
                        contentName = contentName,
                        onDismiss = { selectedIndex = -1 },
                        isReadOnly = true,
                        initialFront = flashcard.frontDescription,
                        initialBack = flashcard.backDescription,
                        flashcard = flashcard,
                        currentIndex = selectedIndex + 1,
                        totalCount = filteredFlashcards.size,
                        onNext = { if (selectedIndex < filteredFlashcards.size - 1) selectedIndex++ },
                        onPrevious = { if (selectedIndex > 0) selectedIndex-- }
                    )
                }
            }
        }
    }
    
    flashcardToDelete?.let { flashcard ->
        AlertDialog(
            onDismissRequest = { flashcardToDelete = null },
            title = { Text("Excluir Flashcard?") },
            text = { Text("Deseja realmente excluir este flashcard?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        flashcardViewModel.onEvent(FlashcardEvent.DeleteFlashcard(flashcard))
                        flashcardToDelete = null
                    }
                ) {
                    Text("Excluir", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { flashcardToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ModernFlashcardItem(flashcard: Flashcard, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = Color.Gray.copy(alpha = 0.6f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = flashcard.frontDescription,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Excluir",
                        tint = Color(0xFFE91E63).copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.Gray.copy(alpha = 0.4f)
                )
            }
        }
    }
}
