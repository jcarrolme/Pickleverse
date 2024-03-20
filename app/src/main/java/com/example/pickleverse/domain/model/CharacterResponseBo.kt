package com.example.pickleverse.domain.model

import com.example.pickleverse.data.model.CharacterDto
import com.example.pickleverse.data.model.InfoDto

data class CharacterResponseBo(
    val info: Info?,
    val results: List<Character>?
)
