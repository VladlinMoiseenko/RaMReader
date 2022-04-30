package ru.vladlin.ram_reader.domain.boundaries

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel

interface CharacterRepo
{
    fun getCharacter(idCharacter: Long ): Flow<CharacterModel?>

    fun getAllLocalCharacters(): Flow<List<CharacterModel>>
    fun saveCharacter(idCharacter: Long): Flow<Boolean>
    fun isSavedCharacter(idCharacter: Long): Flow<Boolean>

    fun getAllNetCharactersFilter(characterName: String?,
                                  characterStatus: String?,
                                  characterGender: String?,
                                  page: Int?): Flow<Response<CharacterList>>
    fun getAllNetCharacters(): Flow<Response<CharacterList>>

}