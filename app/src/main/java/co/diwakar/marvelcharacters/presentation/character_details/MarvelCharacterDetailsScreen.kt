package co.diwakar.marvelcharacters.presentation.character_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import co.diwakar.marvelcharacters.domain.model.MarvelCharacter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@Destination
@Composable
fun MarvelCharacterDetailsScreen(
    marvelCharacter: MarvelCharacter,
    viewModel: MarvelCharacterDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()
    state.errorMessage?.let { message ->
        rememberCoroutineScope().launch {
            scaffoldState.snackbarHostState.showSnackbar(message)
            viewModel.onEvent(MarvelCharacterDetailsEvent.ResetError)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(
                    MarvelCharacterDetailsEvent.FetchDetails(characterId = marvelCharacter.id)
                )
            }
        ) {

        }
    }
}