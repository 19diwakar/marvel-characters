package co.diwakar.marvelcharacters.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.diwakar.marvelcharacters.presentation.characters_listings.MarvelCharactersListingScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MarvelCharactersListingScreen.route
    ) {
        addMarvelCharactersListingScreen(navController)
    }
}

private fun NavGraphBuilder.addMarvelCharactersListingScreen(
    navController: NavController
) {
    composable(route = Screen.MarvelCharactersListingScreen.route) {
        MarvelCharactersListingScreen()
    }
}