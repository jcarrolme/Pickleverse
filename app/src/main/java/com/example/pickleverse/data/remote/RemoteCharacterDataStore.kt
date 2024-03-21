package com.example.pickleverse.data.remote

import com.example.pickleverse.data.core.CustomResult
import com.example.pickleverse.data.model.toDomain
import com.example.pickleverse.domain.model.CharacterResponseBo
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class RemoteCharacterDataStore @Inject constructor(private val characterApi: CharacterApi) {

    suspend fun getCharacters(): CustomResult<CharacterResponseBo> {
        return try {
            val response = characterApi.getCharacters()
            if (response.isSuccessful) {
                response.body()?.let { items ->
                    CustomResult.Success(items.toDomain())
                } ?: CustomResult.Error(Exception("null list of Characters"))
            } else {
                CustomResult.Error(Exception("getCharacters request failed with error ${response.errorBody()}"))
            }
        } catch (e: IOException) {
            CustomResult.Error(Exception("Remote api call failed with exception: $e"))
        }
    }

    suspend fun getCharactersByName(query: String): CustomResult<CharacterResponseBo> {
        return try {
            val response = characterApi.getCharactersByName(query)
            if (response.isSuccessful) {
                response.body()?.let { items ->
                    CustomResult.Success(items.toDomain())
                } ?: CustomResult.Error(Exception("Null list of Characters"))
            } else {
                CustomResult.Error(Exception("getCharactersByName request failed with error ${response.errorBody()}"))
            }
        } catch (e: IOException) {
            CustomResult.Error(Exception("Remote api call failed with exception: $e"))
        }
    }
}

