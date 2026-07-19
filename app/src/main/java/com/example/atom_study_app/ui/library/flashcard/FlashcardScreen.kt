package com.example.atom_study_app.ui.library.flashcard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atom_study_app.data.model.Flashcard
import com.example.atom_study_app.ui.theme.RoyalBlue
import com.example.atom_study_app.ui.theme.RoyalBlueDark

@Composable
fun FlashcardScreen(
    modifier: Modifier = Modifier,
    state: FlashcardState,
    onEvent: (FlashcardEvent) -> Unit,
    subjectName: String,
    contentName: String = "",
    onDismiss: () -> Unit,
    isReadOnly: Boolean = false,
    initialFront: String = "",
    initialBack: String = "",
    flashcard: Flashcard? = null,
    currentIndex: Int = 1,
    totalCount: Int = 1,
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {}
) {
    var isFlipped by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(flashcard) {
        isFlipped = false
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    val cardRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "cardFlipRotation"
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(RoyalBlue, RoyalBlueDark)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = subjectName, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                    Text(text = contentName, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isReadOnly) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "$currentIndex de $totalCount", color = Color.White, fontSize = 14.sp)
                    Text(text = "${if (totalCount > 0) (currentIndex * 100 / totalCount) else 0}%", color = Color.White, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { if (totalCount > 0) currentIndex.toFloat() / totalCount.toFloat() else 0f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            AnimatedContent(
                targetState = flashcard,
                transitionSpec = {
                    fadeIn(tween(300)) + slideInHorizontally { it } togetherWith
                            fadeOut(tween(300)) + slideOutHorizontally { -it }
                },
                label = "flashcardTransition"
            ) { targetFlashcard ->
                Card(
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .graphicsLayer {
                            rotationY = cardRotation
                            cameraDistance = 12f * density
                        }
                        .clickable { 
                            if (isReadOnly) isFlipped = !isFlipped 
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4A7DFF)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isFlipped) {
                            Box(modifier = Modifier.graphicsLayer { rotationY = 180f }) {
                                if (isReadOnly) {
                                    Text(
                                        text = targetFlashcard?.backDescription ?: initialBack,
                                        modifier = Modifier.padding(24.dp),
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center
                                    )
                                } else {
                                    TextField(
                                        value = state.backDescription,
                                        onValueChange = { onEvent(FlashcardEvent.SetBackDescription(it)) },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center
                                        ),
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            cursorColor = Color.White
                                        ),
                                        placeholder = {
                                            Text(
                                                "Escreva o verso...",
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                color = Color.White.copy(alpha = 0.5f)
                                            )
                                        }
                                    )
                                }
                            }
                        } else {
                            if (isReadOnly) {
                                Text(
                                    text = targetFlashcard?.frontDescription ?: initialFront,
                                    modifier = Modifier.padding(24.dp),
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                TextField(
                                    value = state.frontDescription,
                                    onValueChange = { onEvent(FlashcardEvent.SetFrontDescription(it)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center
                                    ),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = Color.White
                                    ),
                                    placeholder = {
                                        Text(
                                            "Escreva a frente...",
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White.copy(alpha = 0.5f)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (!isReadOnly) {
                Button(
                    onClick = { isFlipped = !isFlipped },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
                ) {
                    Text(if (isFlipped) "Ver Frente" else "Ver Verso", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isReadOnly) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularButton(icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft, color = Color.White.copy(alpha = 0.2f)) {
                        onPrevious()
                    }

                    CircularButton(icon = Icons.Default.Close, color = Color(0xFFE91E63).copy(alpha = 0.4f)) {
                        flashcard?.let { onEvent(FlashcardEvent.UpdateScore(it, false)) }
                        onDismiss()
                    }

                    CircularButton(icon = Icons.Default.Check, color = Color(0xFF009688).copy(alpha = 0.4f)) {
                        flashcard?.let { onEvent(FlashcardEvent.UpdateScore(it, true)) }
                        onDismiss()
                    }

                    CircularButton(
                        icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        color = Color.White,
                        iconColor = RoyalBlue,
                        size = 64.dp
                    ) {
                        onNext()
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
                    ) {
                        Text("Descartar", color = Color.White)
                    }
                    Button(
                        onClick = {
                            onEvent(FlashcardEvent.SaveFlashcard("$subjectName/$contentName"))
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Adicionar", color = RoyalBlue)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CircularButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    iconColor: Color = Color.White,
    size: androidx.compose.ui.unit.Dp = 50.dp,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(size)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = color
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(size * 0.6f))
        }
    }
}
