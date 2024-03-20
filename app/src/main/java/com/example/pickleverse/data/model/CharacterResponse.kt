package com.example.pickleverse.data.model

import com.example.pickleverse.domain.model.Character
import com.example.pickleverse.domain.model.CharacterLocation
import com.example.pickleverse.domain.model.CharacterOrigin
import com.example.pickleverse.domain.model.CharacterResponseBo
import com.example.pickleverse.domain.model.Info
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: InfoDto?,
    @SerializedName("results")
    val results: List<CharacterDto>
)

data class CharacterDto (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("origin")
    val origin: CharacterOriginDto?,
    @SerializedName("location")
    val location: CharacterLocationDto?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("episode")
    val episode: List<String>?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("created")
    val created: String
)

data class CharacterOriginDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class CharacterLocationDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class InfoDto (
    @SerializedName("count")
    val count: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)

fun CharacterResponse.toDomain(): CharacterResponseBo {
    val list = results.map { dto ->
        Character(
            id = dto.id,
            name = dto.name,
            status = dto.status,
            species = dto.species,
            type = dto.type,
            gender = dto.gender,
            origin = dto.origin?.toDomain(dto.origin),
            location = dto.location?.toDomain(dto.location),
            image = dto.image,
            episode = dto.episode,
            url = dto.url,
            created = dto.created
        )
    }
    return CharacterResponseBo(info?.toDomain(info), list)
}

fun CharacterOriginDto.toDomain(dto: CharacterOriginDto): CharacterOrigin =
    CharacterOrigin(
        name = dto.name,
        url = dto.url
    )

fun CharacterLocationDto.toDomain(dto: CharacterLocationDto): CharacterLocation =
    CharacterLocation(
        name = dto.name,
        url = dto.url
    )

fun InfoDto.toDomain(dto: InfoDto): Info =
    Info(
        count = dto.count,
        pages = dto.pages,
        next = dto.next,
        prev = dto.prev
    )





