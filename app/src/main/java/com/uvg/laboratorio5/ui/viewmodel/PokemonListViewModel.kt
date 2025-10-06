package com.uvg.laboratorio5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.laboratorio5.data.remote.RetrofitClient
import com.uvg.laboratorio5.data.repository.PokemonRepository
import com.uvg.laboratorio5.ui.state.PokemonListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    private val repository = PokemonRepository(RetrofitClient.apiService)

    private val _uiState = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    val uiState: StateFlow<PokemonListState> = _uiState.asStateFlow()

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            _uiState.value = PokemonListState.Loading

            repository.getPokemonList(100).fold(
                onSuccess = { response ->
                    _uiState.value = PokemonListState.Success(response.results)
                },
                onFailure = { exception ->
                    _uiState.value = PokemonListState.Error(
                        exception.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    fun retry() {
        loadPokemonList()
    }
}