package com.example.pickleverse.data.remote

import com.example.pickleverse.data.model.CharacterDetailDto
import com.example.pickleverse.data.model.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterListResponse>

    @GET("character")
    suspend fun getCharactersByName(@Query("name") name: String): Response<CharacterListResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<CharacterDetailDto>

}