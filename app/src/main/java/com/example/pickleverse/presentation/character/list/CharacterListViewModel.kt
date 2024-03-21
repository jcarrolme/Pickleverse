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

    private var highlightedLettersList : List<Char> = listOf()

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ListUiState.InitialLoading

            // Simulated delay
            delay(DELAY)

            try {
                when (val response = getCharactersUseCase()) {
                    is CustomResult.Success -> {
                        if (response.data != null) {
                            response.data.results?.let { results ->
                                _uiState.value = ListUiState.Success(results, highlightedLettersList)
                            }
                        } else {
                            _uiState.value = ListUiState.Success(emptyList(), highlightedLettersList)
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
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ListUiState.Loading

            try {
                when (val response = getCharactersByNameUseCase(query)) {
                    is CustomResult.Success -> {
                        if (response.data != null) {
                            response.data.results?.let { results ->
                                _uiState.value = ListUiState.Success(results,highlightedLettersList)
                            }
                        } else {
                            _uiState.value = ListUiState.Success(emptyList(), highlightedLettersList)
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

    fun updateHighlightedLetters(newLetters: List<Char>) {
        highlightedLettersList = newLetters
    }

    companion object {
        private const val DELAY = 1000L
    }
}