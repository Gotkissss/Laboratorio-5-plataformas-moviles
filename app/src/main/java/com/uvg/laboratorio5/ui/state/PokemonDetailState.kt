package com.uvg.laboratorio5.ui.state

import com.uvg.laboratorio5.data.remote.PokemonDetailResponse

sealed class PokemonDetailState {
    data object Loading : PokemonDetailState()
    data class Success(val pokemon: PokemonDetailResponse) : PokemonDetailState()
    data class Error(val message: String) : PokemonDetailState()
}