package com.example.pickleverse.presentation.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.domain.usecase.GetCharactersByNameUseCase
import com.example.pickleverse.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersByNameUseCase: GetCharactersByNameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.InitialLoading)
    val uiState: StateFlow<ListUiState> = _uiState

    private var highlightedLetters: String = ""
    var initLoading: Boolean = true

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ListUiState.InitialLoading
            initLoading = true

            // Simulated delay
            delay(DELAY)

            try {
                when (val response = getCharactersUseCase()) {
                    is CustomResult.Success -> {
                        if (response.data != null) {
                            response.data.results?.let { results ->
                                _uiState.value = ListUiState.Success(results, highlightedLetters)
                            }
                        } else {
                            _uiState.value = ListUiState.Success(emptyList(), highlightedLetters)
                        }
                    }
                    is CustomResult.Error -> {
                        response.errorType.message?.let { errorMessage ->
                            _uiState.value = ListUiState.Error(errorMessage)
                        }
                    }
                }
            } catch (e: Exception) {
                e.message?.let { errorMessage ->
                    _uiState.value = ListUiState.Error(errorMessage)
                }
            }
        }
    }

    fun searchCharactersByName(query: String) {
        highlightedLetters = query
        if (initLoading) initLoading = false
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ListUiState.Loading

            try {
                when (val response = getCharactersByNameUseCase(query)) {
                    is CustomResult.Success -> {
                        if (response.data != null) {
                            response.data.results?.let { results ->
                                _uiState.value = ListUiState.Success(results,highlightedLetters)
                            }
                        } else {
                            _uiState.value = ListUiState.Success(emptyList(), highlightedLetters)
                        }
                    }
                    is CustomResult.Error -> {
                        response.errorType.message?.let { errorMessage ->
                            _uiState.value = ListUiState.Error(errorMessage)
                        }
                    }
                }
            } catch (e: Exception) {
                e.message?.let { errorMessage ->
                    _uiState.value = ListUiState.Error(errorMessage)
                }
            }
        }
    }

    fun updateHighlightedLetters(newLetters: String) {
        highlightedLetters = newLetters
    }

    companion object {
        private const val DELAY = 2500L
    }
}