package com.example.pickleverse.presentation.character.list

sealed class ListUiState {
    object Loading : ListUiState()
    object HideLoading : ListUiState()
    object Success : ListUiState()
    object Error : ListUiState()

}