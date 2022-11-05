package co.diwakar.marvelcharacters.presentation.character_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import co.diwakar.marvelcharacters.config.BaseViewModel
import co.diwakar.marvelcharacters.domain.model.MarvelCharacter
import co.diwakar.marvelcharacters.domain.repository.MarvelCharactersRepository
import co.diwakar.marvelcharacters.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelCharacterDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MarvelCharactersRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(MarvelCharacterDetailsState())
    val state = _state as StateFlow<MarvelCharacterDetailsState>

    init {
        viewModelScope.launch {
            val characterId =
                savedStateHandle.get<MarvelCharacter>("marvelCharacter")?.id ?: return@launch
            fetchCharacterDetails(characterId = characterId)
        }
    }

    fun onEvent(event: MarvelCharacterDetailsEvent) {
        when (event) {
            is MarvelCharacterDetailsEvent.FetchDetails -> {
                event.characterId?.let { characterId ->
                    fetchCharacterDetails(characterId)
                }
            }
            is MarvelCharacterDetailsEvent.ResetError -> onError(null)
        }
    }

    private fun fetchCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            repository.getMarvelCharacter(characterId)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> onDetailsRequestSuccess(result.data)
                        is Resource.Error -> onError(result.message)
                        is Resource.Loading -> onLoadUpdated(result.isLoading)
                    }
                }
        }
    }

    private fun onDetailsRequestSuccess(character: MarvelCharacter?) {
        character?.let { toSet ->
            _state.update {
                it.copy(character = toSet)
            }
        }
    }

    override fun onError(message: String?) {
        _state.update {
            it.copy(errorMessage = message)
        }
    }

    override fun onLoadUpdated(isLoading: Boolean) {
        _state.update {
            it.copy(isLoading = isLoading)
        }
    }
}