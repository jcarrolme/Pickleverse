package com.example.pickleverse.data.remote

import com.example.pickleverse.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponse>

    @GET("character")
    suspend fun getCharactersByName(@Query("name") name: String): Response<CharacterResponse>
}