package com.example.pickleverse.presentation.character.list

import com.example.pickleverse.domain.model.CharacterDetail

sealed class ListUiState {
    object InitialLoading : ListUiState()
    object Loading : ListUiState()
    object HideLoading : ListUiState()
    data class Success(val list: List<CharacterDetail>, val highlightedLetterList: List<Char>) : ListUiState()
    data class Error(val message: String) : ListUiState()
}