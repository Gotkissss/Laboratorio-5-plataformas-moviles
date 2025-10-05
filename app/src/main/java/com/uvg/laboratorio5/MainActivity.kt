package com.uvg.laboratorio5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.laboratorio5.ui.PokemonDetailScreen
import com.uvg.laboratorio5.ui.PokemonListScreen
import com.uvg.laboratorio5.ui.theme.Laboratorio5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "pokemon_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("pokemon_list") {
                            PokemonListScreen(navController)
                        }
                        composable("pokemon_detail/{pokemonId}") { backStackEntry ->
                            val pokemonId = backStackEntry.arguments?.getString("pokemonId")?.toIntOrNull()
                            if (pokemonId != null) {
                                PokemonDetailScreen(pokemonId)
                            }
                        }
                    }
                }
            }
        }
    }
}