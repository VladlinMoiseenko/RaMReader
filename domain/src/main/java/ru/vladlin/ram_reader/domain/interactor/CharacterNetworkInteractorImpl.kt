package ru.vladlin.ram_reader.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.vladlin.ram_reader.domain.boundaries.CharacterNetworkInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterRepo
import ru.vladlin.ram_reader.domain.model.CharacterList

class CharacterNetworkInteractorImpl (val characterRepo: CharacterRepo): CharacterNetworkInteractor
{
    override fun getAllNetCharactersFilter(characterName: String?,
                                           characterStatus: String?,
                                           characterGender: String?,
                                           page: Int?): Flow<Response<CharacterList>> {
        return characterRepo.getAllNetCharactersFilter(
            characterName,
            characterStatus,
            characterGender,
            page
        ).flowOn(Dispatchers.IO)
    }

    override fun getAllNetCharacters(): Flow<Response<CharacterList>> {
        return characterRepo.getAllNetCharacters().flowOn(Dispatchers.IO)
    }

}