package com.example.pickleverse.domain.model

data class CharacterDetail(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterOrigin?,
    val location: CharacterLocation?,
    val image: String?,
    val episode: List<String>?,
    val url: String?,
    val created: String
)

data class CharacterOrigin(
    val name: String?,
    val url: String?
)

data class CharacterLocation(
    val name: String?,
    val url: String?
)
