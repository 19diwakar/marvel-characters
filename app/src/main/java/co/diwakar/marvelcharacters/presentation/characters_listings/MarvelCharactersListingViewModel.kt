package co.diwakar.marvelcharacters.presentation.characters_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.diwakar.marvelcharacters.domain.repository.MarvelCharactersRepository
import co.diwakar.marvelcharacters.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelCharactersListingViewModel @Inject constructor(
    private val repository: MarvelCharactersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MarvelCharactersListingState())
    val state = _state as StateFlow<MarvelCharactersListingState>
    private var searchJob: Job? = null

    init {
        getMarvelCharactersListingsFromCache()
        getMarvelCharactersListings()
    }

    fun onEvent(event: MarvelCharactersListingEvent) {
        when (event) {
            is MarvelCharactersListingEvent.Refresh -> {
                _state.update {
                    it.copy(offset = 0)
                }
                getMarvelCharactersListings()
            }
            is MarvelCharactersListingEvent.FetchNextPage -> {
                if (state.value.isPaginationEnabled && state.value.isLoading.not()) {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                    getMarvelCharactersListings()
                }
            }
            is MarvelCharactersListingEvent.OnSearchQueryChange -> {
                _state.update {
                    val query = event.query.ifBlank { null }
                    it.copy(searchQuery = query, offset = 0)
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getMarvelCharactersListings()
                }
            }
        }
    }

    private fun getMarvelCharactersListingsFromCache() {
        viewModelScope.launch {
            repository.getMarvelCharactersFromCache().collect { result ->
                result.data?.let { characters ->
                    _state.update {
                        it.copy(characters = characters)
                    }
                }
            }
        }
    }

    private fun getMarvelCharactersListings() {
        viewModelScope.launch {
            repository.getMarvelCharacters(
                limit = LIMIT, offset = state.value.offset, query = state.value.searchQuery
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { data ->
                            _state.update {
                                //if previous characters are fetched from local
                                //then we will not consider them
                                val prevCharacters =
                                    if (it.offset == 0) emptyList() else it.characters
                                val newCharacters =
                                    listOf(prevCharacters, data.results ?: emptyList()).flatten()
                                val newOffset = it.offset + (data.count ?: 0)

                                it.copy(
                                    characters = newCharacters,
                                    offset = newOffset,
                                    isPaginationEnabled = newOffset < (data.total ?: 0)
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(errorMessage = it.errorMessage)
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val LIMIT = 10
    }
}