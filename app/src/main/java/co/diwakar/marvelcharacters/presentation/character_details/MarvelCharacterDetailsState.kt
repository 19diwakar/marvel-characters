package co.diwakar.marvelcharacters.presentation.character_details

import co.diwakar.marvelcharacters.domain.model.MarvelCharacter

data class MarvelCharacterDetailsState(
    val character: MarvelCharacter? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
)