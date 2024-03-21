package com.example.pickleverse.presentation.character.list

import com.example.pickleverse.domain.model.Character

sealed class ListUiState {
    object InitialLoading : ListUiState()
    object Loading : ListUiState()
    object HideLoading : ListUiState()
    data class Success(val list: List<Character>, val highlightedLetterList: List<Char>) : ListUiState()
    data class Error(val message: String) : ListUiState()
}