package com.example.pickleverse.presentation.character.list

import com.example.pickleverse.domain.model.Character

sealed class ListUiState {
    object Loading : ListUiState()
    object HideLoading : ListUiState()
    data class Success(val list: List<Character>) : ListUiState()
    object Error : ListUiState()
}