package com.uvg.laboratorio5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.laboratorio5.data.remote.RetrofitClient
import com.uvg.laboratorio5.data.repository.PokemonRepository
import com.uvg.laboratorio5.ui.state.PokemonDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    private val repository = PokemonRepository(RetrofitClient.apiService)

    private val _uiState = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val uiState: StateFlow<PokemonDetailState> = _uiState.asStateFlow()

    fun loadPokemonDetail(pokemonId: Int) {
        viewModelScope.launch {
            _uiState.value = PokemonDetailState.Loading

            repository.getPokemonDetail(pokemonId).fold(
                onSuccess = { pokemon ->
                    _uiState.value = PokemonDetailState.Success(pokemon)
                },
                onFailure = { exception ->
                    _uiState.value = PokemonDetailState.Error(
                        exception.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    fun retry(pokemonId: Int) {
        loadPokemonDetail(pokemonId)
    }
}