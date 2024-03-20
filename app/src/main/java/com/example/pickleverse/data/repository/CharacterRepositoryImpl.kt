package com.example.pickleverse.data.repository

import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.data.remote.CharacterApi
import com.example.pickleverse.domain.model.CharacterResponseBo
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterDataStore: CharacterDataStore): CharacterRepository {
    override suspend fun getCharacters(): CustomResult<CharacterResponseBo> {
        return characterDataStore.getCharacters()
    }
}