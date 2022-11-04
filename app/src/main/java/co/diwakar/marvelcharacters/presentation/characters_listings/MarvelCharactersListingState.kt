package co.diwakar.marvelcharacters.presentation.characters_listings

import co.diwakar.marvelcharacters.domain.model.MarvelCharacter

data class MarvelCharactersListingState(
    val characters: List<MarvelCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String? = null,
    val offset: Int = 0,
    val totalCount: Int? = null
) {
    fun isPaginationEnabled(): Boolean {
        return (totalCount ?: 0) <= offset
    }
}