package ru.vladlin.ram_reader.data.character.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import ru.vladlin.ram_reader.data.character.dataStore.LocalCharacterDataStore
import ru.vladlin.ram_reader.data.character.dataStore.NetCharacterDataStore
import ru.vladlin.ram_reader.domain.boundaries.CharacterRepo
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel

class CharacterRepoImpl(
    val localCharacterDataStore: LocalCharacterDataStore,
    val netCharacterDataStore: NetCharacterDataStore
    ) : CharacterRepo
{

    override fun getAllNetCharactersFilter(characterName: String?,
                                           characterStatus: String?,
                                           characterGender: String?,
                                           page: Int?): Flow<Response<CharacterList>> {
        return flow {
            emit(netCharacterDataStore.getCharactersFilter(
                characterName,
                characterStatus,
                characterGender,
                page
            ))
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllNetCharacters(): Flow<Response<CharacterList>> {
        return flow {
            emit(netCharacterDataStore.getCharacters())
        }.flowOn(Dispatchers.IO)
    }

    override fun getCharacter(characterId: Long): Flow<CharacterModel?> {
        return flow {
            emit(netCharacterDataStore.getCharacter(characterId))
        }
    }

    override fun saveCharacter(characterId: Long): Flow<Boolean> {
        return flow {
            if (localCharacterDataStore.characterIsSaved(characterId))
            {
                localCharacterDataStore.removeCharacter(characterId)
            } else {
                localCharacterDataStore.saveCharacter(netCharacterDataStore.getCharacter(characterId))
            }
            emit(localCharacterDataStore.characterIsSaved(characterId))
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllLocalCharacters(): Flow<List<CharacterModel>> {
        return flow {
            emit(localCharacterDataStore.getAllSavedCharacters())
        }.flowOn(Dispatchers.IO)
    }

    override fun isSavedCharacter(idCharacter: Long): Flow<Boolean> {
        return flow { emit(localCharacterDataStore.characterIsSaved(idCharacter)) }.flowOn(Dispatchers.IO)
    }
}