package com.example.pickleverse.data.remote

import com.example.pickleverse.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponse>
}