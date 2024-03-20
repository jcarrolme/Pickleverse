package com.example.pickleverse.data.repository

import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.domain.model.CharacterResponseBo


interface CharacterRepository {
    suspend fun getCharacters(): CustomResult<CharacterResponseBo>
}