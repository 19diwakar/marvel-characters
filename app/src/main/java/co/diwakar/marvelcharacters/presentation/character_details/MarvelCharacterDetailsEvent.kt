package co.diwakar.marvelcharacters.presentation.character_details

sealed class MarvelCharacterDetailsEvent {
    object ResetError : MarvelCharacterDetailsEvent()
    data class FetchDetails(val characterId: Int?) : MarvelCharacterDetailsEvent()
}