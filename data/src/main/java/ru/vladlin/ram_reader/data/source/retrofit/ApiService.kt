package ru.vladlin.ram_reader.data.source.retrofit

import ru.vladlin.ram_reader.domain.model.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.vladlin.ram_reader.domain.model.CharacterModel

interface ApiService {

    @GET("character")
    suspend fun getAllCharactersFilter(
        @Query("name") characterName: String?,
        @Query("status") characterStatus: String?,
        @Query("gender") characterGender: String?,
        @Query("page") page: Int?
    ): Response<CharacterList>

    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterList>

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId: Long
    ): Response<CharacterModel>

}