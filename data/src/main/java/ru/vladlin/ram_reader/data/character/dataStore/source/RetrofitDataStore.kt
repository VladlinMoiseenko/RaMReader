package ru.vladlin.ram_reader.data.character.dataStore.source

import retrofit2.Response
import ru.vladlin.ram_reader.data.character.dataStore.NetCharacterDataStore
import ru.vladlin.ram_reader.data.source.retrofit.ApiService
import ru.vladlin.ram_reader.data.utils.BaseDataSource
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel

class RetrofitDataStore(
    val apiService: ApiService
    ) : NetCharacterDataStore, BaseDataSource()
{
    override suspend fun getCharactersFilter(
        characterName: String?,
        characterStatus: String?,
        characterGender: String?,
        page: Int?,
    ) = apiService.getAllCharactersFilter(
        characterName,
        characterStatus,
        characterGender,
        page
    )

    override suspend fun getCharacters(): Response<CharacterList>
    {
        return apiService.getAllCharacters()
    }

    override suspend fun getCharacter(idCharacter: Long): CharacterModel?
    {
        return apiService.getCharacterById(idCharacter).body()
    }
}