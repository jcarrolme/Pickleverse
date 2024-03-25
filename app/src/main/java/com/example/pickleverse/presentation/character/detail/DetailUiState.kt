package com.example.pickleverse.presentation.character.detail

import com.example.pickleverse.domain.model.CharacterDetail

sealed class DetailUiState {
    object Loading : DetailUiState()
    object HideLoading : DetailUiState()
    data class Success(val item: CharacterDetail) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}