package ru.vladlin.ram_reader.data.character.dataStore

import retrofit2.Response
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel

interface NetCharacterDataStore {
    suspend fun getCharactersFilter(characterName: String?,
                                    characterStatus: String?,
                                    characterGender: String?,
                                    page: Int?): Response<CharacterList>
    suspend fun getCharacters(): Response<CharacterList>
    suspend fun getCharacter(idCharacter: Long): CharacterModel?
}