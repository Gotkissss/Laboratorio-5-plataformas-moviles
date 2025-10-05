package com.uvg.laboratorio5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.uvg.laboratorio5.network.PokemonDetailResponse
import com.uvg.laboratorio5.network.RetrofitClient
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun PokemonDetailScreen(pokemonId: Int) {
    val pokemonDetail = remember { mutableStateOf<PokemonDetailResponse?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pokemonId) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.apiService.getPokemonDetail(pokemonId)
                pokemonDetail.value = response
                isLoading.value = false
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
                isLoading.value = false
            }
        }
    }

    when {
        isLoading.value -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        errorMessage.value != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage.value ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        pokemonDetail.value != null -> {
            val pokemon = pokemonDetail.value!!
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