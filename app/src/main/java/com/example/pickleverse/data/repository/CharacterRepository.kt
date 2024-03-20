package com.example.pickleverse.data.repository

import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.data.remote.RemoteCharacterDataStore
import com.example.pickleverse.domain.model.CharacterResponseBo
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val characterDataStore: RemoteCharacterDataStore) {
    suspend fun getCharacters(): CustomResult<CharacterResponseBo> {
        return characterDataStore.getCharacters()
    }

    suspend fun getCharactersByName(query: String): CustomResult<CharacterResponseBo> {
        return characterDataStore.getCharactersByName(query)
    }
}