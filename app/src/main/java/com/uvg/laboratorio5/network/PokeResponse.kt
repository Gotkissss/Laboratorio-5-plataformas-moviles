package com.uvg.laboratorio5.network

data class PokeListResponse(
    val results: List<PokemonBasic>
)

data class PokemonBasic(
    val name: String,
    val url: String
) {
    val id: Int
        get() = url.trimEnd('/').split('/').last().toInt()
}

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites
)

data class Sprites(
    val front_default: String?,
    val back_default: String?,
    val front_shiny: String?,
    val back_shiny: String?
)