package ru.vladlin.ram_reader.domain.boundaries

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel

interface CharacterNetworkInteractor
{
    fun getAllNetCharactersFilter(characterName: String?,
                                  characterStatus: String?,
                                  characterGender: String?,
                                  page: Int?): Flow<Response<CharacterList>>

    fun getAllNetCharacters(): Flow<Response<CharacterList>>

}