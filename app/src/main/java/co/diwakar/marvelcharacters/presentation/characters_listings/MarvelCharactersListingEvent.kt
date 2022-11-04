package co.diwakar.marvelcharacters.presentation.characters_listings

sealed class MarvelCharactersListingEvent {
    object Refresh : MarvelCharactersListingEvent()
    object FetchNextPage: MarvelCharactersListingEvent()
    data class OnSearchQueryChange(val query: String) : MarvelCharactersListingEvent()
}