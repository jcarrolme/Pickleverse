package com.example.pickleverse.domain.usecase

import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.data.repository.CharacterRepository
import com.example.pickleverse.domain.model.CharacterDetail
import javax.inject.Inject

class GetCharacterDetailByIdUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(id: Int): CustomResult<CharacterDetail> = repository.getCharacterById(id)
}