package com.example.atom_study_app.ui.library.flashcard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atom_study_app.ui.theme.AtomstudyappTheme
import com.example.atom_study_app.ui.theme.BlueAccent
import com.example.atom_study_app.ui.theme.RedAlert

@Preview(showBackground = true)
@Composable
fun FlashScreenPreview() {

    AtomstudyappTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            FlashcardScreen(
                state = FlashcardState(
                    frontDescription = "",
                    backDescription = ""
                ),
                onEvent = {}
            )

        }
    }
}

@Composable
fun FlashcardScreen(
    modifier: Modifier = Modifier,
    state: FlashcardState,
    onEvent: (FlashcardEvent) -> Unit
) {
    var isFlipped by remember { mutableStateOf(false) }

    val cardRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "cardFlipRotation"
    )

    val maxChar = 250

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        Text(
            text = if (isFlipped) "Verso do Flashcard" else "Criando flashcard...",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(350.dp)
                .height(350.dp)
                .graphicsLayer {
                    rotationY = cardRotation
                    cameraDistance = 12f * density
                },
            colors = CardDefaults.cardColors(
                containerColor = if (isFlipped)
                    MaterialTheme.colorScheme.secondaryContainer
                else
                    MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                if (isFlipped) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .graphicsLayer {
                                rotationY = 180f
                            },
                        verticalArrangement = Arrangement.Center
                    ) {

                        TextField(
                            value = state.backDescription,
                            onValueChange = { newText ->
                                if (newText.length <= maxChar) {
                                    onEvent(FlashcardEvent.SetBackDescription(newText))
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                        ))
                    }

                } else {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        TextField(
                            value = state.frontDescription,
                            onValueChange = { newValue ->
                                onEvent(FlashcardEvent.SetFrontDescription(newValue))
                            },

                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier.padding(15.dp)
        ) {

            Text(
                text = if (isFlipped) "Desvirar" else "Virar Flashcard",
                color = BlueAccent,
                modifier = Modifier.clickable {
                    isFlipped = !isFlipped
                }
            )

            Text(
                text = "Adicionar",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable { }
            )

            Text(
                text = "Descartar",
                color = RedAlert,
                modifier = Modifier.clickable { }
            )
        }
    }
}