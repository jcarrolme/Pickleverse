package com.example.pickleverse.presentation.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.domain.model.Character
import com.example.pickleverse.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    val characterList: MutableList<Character> = mutableListOf()
    private val mUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = mUiState

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            mUiState.value = ListUiState.Loading
            try {
                when (val response = getCharactersUseCase()) {
                    is CustomResult.Success -> {
                        if (response.data != null) {
                            response.data.results?.let { characterList.addAll(it) }
                            mUiState.value = ListUiState.Success(characterList)
                        }
                    }
                    is CustomResult.Error -> {
                        mUiState.value = ListUiState.Error
                    }
                }
            } catch (e: Exception){
                mUiState.value = ListUiState.Error
            }
            mUiState.value = ListUiState.HideLoading
        }
    }
}