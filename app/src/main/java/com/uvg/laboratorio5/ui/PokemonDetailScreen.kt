package com.uvg.laboratorio5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.uvg.laboratorio5.ui.state.PokemonDetailState
import com.uvg.laboratorio5.ui.viewmodel.PokemonDetailViewModel
import java.util.Locale

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    viewModel: PokemonDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    when (val state = uiState) {
        is PokemonDetailState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is PokemonDetailState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { viewModel.retry(pokemonId) }) {
                    Text("Reintentar")
                }
            }
        }
        is PokemonDetailState.Success -> {
            val pokemon = state.pokemon
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                        else it.toString()
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Primera fila: Front y Back
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Front", style = MaterialTheme.typography.bodyMedium)
                        Image(
                            painter = rememberAsyncImagePainter(model = pokemon.sprites.front_default),
                            contentDescription = "Front",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Back", style = MaterialTheme.typography.bodyMedium)
                        Image(
                            painter = rememberAsyncImagePainter(model = pokemon.sprites.back_default),
                            contentDescription = "Back",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Segunda fila: Front Shiny y Back Shiny
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Front Shiny", style = MaterialTheme.typography.bodyMedium)
                        Image(
                            painter = rememberAsyncImagePainter(model = pokemon.sprites.front_shiny),
                            contentDescription = "Front Shiny",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Back Shiny", style = MaterialTheme.typography.bodyMedium)
                        Image(
                            painter = rememberAsyncImagePainter(model = pokemon.sprites.back_shiny),
                            contentDescription = "Back Shiny",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
            }
        }
    }
}