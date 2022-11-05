package co.diwakar.marvelcharacters.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.diwakar.marvelcharacters.config.Extras
import co.diwakar.marvelcharacters.domain.model.MarvelCharacter
import co.diwakar.marvelcharacters.presentation.character_details.MarvelCharacterDetailsScreen
import co.diwakar.marvelcharacters.presentation.characters_listings.MarvelCharactersListingScreen
import co.diwakar.marvelcharacters.util.getParcelableData

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MarvelCharactersListingScreen.route
    ) {
        addMarvelCharactersListingScreen(navController)
        addMarvelCharacterDetailsScreen()
    }
}

private fun NavGraphBuilder.addMarvelCharactersListingScreen(
    navController: NavController
) {
    composable(route = Screen.MarvelCharactersListingScreen.route) {
        MarvelCharactersListingScreen(
            navigateToMarvelCharacterDetailsScreen = { character ->
                navController.navigateToMarvelCharacterDetailsScreen(character)
            }
        )
    }
}

private fun NavGraphBuilder.addMarvelCharacterDetailsScreen() {
    composable(route = Screen.MarvelCharacterDetailsScreen.route) {
        it.arguments
            ?.getParcelableData<MarvelCharacter>(Extras.MARVEL_CHARACTER)
            ?.let { marvelCharacter ->
                MarvelCharacterDetailsScreen(marvelCharacter)
            }
    }
}

private fun NavController.navigateToMarvelCharacterDetailsScreen(character: MarvelCharacter) {
    currentBackStackEntry?.arguments?.apply {
        putParcelable(Extras.MARVEL_CHARACTER, character)
    }
    navigate(Screen.MarvelCharacterDetailsScreen.route)
}