package co.diwakar.marvelcharacters.navigation

sealed class Screen(val route: String) {
    object MarvelCharactersListingScreen : Screen("marvel-characters-listing-screen")
    object MarvelCharacterDetailsScreen : Screen("marvel-character-details-screen")
}