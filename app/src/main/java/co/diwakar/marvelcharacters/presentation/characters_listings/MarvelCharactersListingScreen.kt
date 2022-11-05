package co.diwakar.marvelcharacters.presentation.characters_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.diwakar.marvelcharacters.domain.model.MarvelCharacter
import co.diwakar.marvelcharacters.ui.composables.ShimmerAnimation
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MarvelCharactersListingScreen() {
    val viewModel: MarvelCharactersListingViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    Column(modifier = Modifier.fillMaxSize()) {
        SearchCompose(
            state.searchQuery,
            onValueChanged = {
                viewModel.onEvent(
                    MarvelCharactersListingEvent.OnSearchQueryChange(it)
                )
            }
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(MarvelCharactersListingEvent.Refresh)
            }
        ) {
            CharactersList(
                characters = state.characters,
                isLoading = state.isLoading,
                loadNextPage = {
                    viewModel.onEvent(MarvelCharactersListingEvent.FetchNextPage)
                }
            )
        }
    }
}

@Composable
fun CharactersList(
    characters: List<MarvelCharacter>,
    isLoading: Boolean,
    loadNextPage: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
    ) {
        items(characters.size) { index ->
            if (index >= characters.size - 1) {
                loadNextPage()
            }
            val character = characters[index]
            MarvelCharacterItem(
                character = character,
                modifier = Modifier.clickable {
                    //navigate to details screen
                }
            )
        }
        items(2) {
            if (isLoading) {
                ShimmerAnimation(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .aspectRatio(0.8f)
                        .clip(CutCornerShape(bottomEnd = 16.dp))
                )
            }
        }
    }
}

@Composable
fun SearchCompose(searchQuery: String?, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = searchQuery ?: "",
        onValueChange = onValueChanged,
        placeholder = {
            Text(text = "Search Characters...")
        },
        maxLines = 1,
        singleLine = true
    )
}