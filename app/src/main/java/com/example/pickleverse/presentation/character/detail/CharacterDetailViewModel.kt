package com.example.pickleverse.presentation.character.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.domain.usecase.GetCharacterDetailByIdUseCase
import com.example.pickleverse.presentation.character.list.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailByIdUseCase: GetCharacterDetailByIdUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = DetailUiState.Loading

            // Simulated delay
            delay(DELAY)

            try {
                when (val response = getCharacterDetailByIdUseCase(id)) {
                    is CustomResult.Success -> {
                        if (response.data != null) {
                            _uiState.value = DetailUiState.Success(response.data)
                        }
                    }
                    is CustomResult.Error -> {
                        response.errorType.message?.let { errorMessage ->
                            _uiState.value = DetailUiState.Error(errorMessage)
                        }
                    }
                }
            } catch (e: Exception) {
                e.message?.let { errorMessage ->
                    _uiState.value = DetailUiState.Error(errorMessage)
                }
            }
        }
    }

    companion object {
        private const val DELAY = 200L
    }
}