package com.example.pickleverse.data.repository

import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.data.model.CharacterResponse
import com.example.pickleverse.domain.model.CharacterResponseBo

interface CharacterDataStore {
    suspend fun getCharacters(): CustomResult<CharacterResponseBo>
}