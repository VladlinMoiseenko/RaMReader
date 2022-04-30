package ru.vladlin.ram_reader.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.vladlin.ram_reader.domain.boundaries.CharacterLocalInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterRepo
import ru.vladlin.ram_reader.domain.model.CharacterModel

class CharacterLocalInteractorImpl(
    val characterRepo: CharacterRepo
    ) : CharacterLocalInteractor
{

    override fun getAllLocalCharacters(): Flow<List<CharacterModel>>
    {
        return characterRepo.getAllLocalCharacters()
    }

    override fun saveCharacter(idCharacter: Long): Flow<Boolean>
    {
        return characterRepo.saveCharacter(idCharacter)
    }

    override fun isSavedCharacter(idCharacter: Long): Flow<Boolean>
    {
        return characterRepo.isSavedCharacter(idCharacter)
    }
}