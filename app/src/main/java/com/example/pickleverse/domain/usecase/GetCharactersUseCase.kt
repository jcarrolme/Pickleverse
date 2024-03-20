package com.example.pickleverse.domain.usecase

import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.data.repository.CharacterRepository
import com.example.pickleverse.domain.model.CharacterResponseBo
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(): CustomResult<CharacterResponseBo> = repository.getCharacters()
}