package com.uvg.laboratorio5.data.repository

import com.uvg.laboratorio5.data.remote.PokeApiService
import com.uvg.laboratorio5.data.remote.PokeListResponse
import com.uvg.laboratorio5.data.remote.PokemonDetailResponse

class PokemonRepository(private val apiService: PokeApiService) {

    suspend fun getPokemonList(limit: Int): Result<PokeListResponse> {
        return try {
            val response = apiService.getPokemonList(limit)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPokemonDetail(id: Int): Result<PokemonDetailResponse> {
        return try {
            val response = apiService.getPokemonDetail(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}