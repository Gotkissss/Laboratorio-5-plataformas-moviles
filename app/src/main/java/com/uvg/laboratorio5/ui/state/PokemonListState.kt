package com.uvg.laboratorio5.ui.state

import com.uvg.laboratorio5.data.remote.PokemonBasic

sealed class PokemonListState {
    data object Loading : PokemonListState()
    data class Success(val pokemonList: List<PokemonBasic>) : PokemonListState()
    data class Error(val message: String) : PokemonListState()
}