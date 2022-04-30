package ru.vladlin.ram_reader.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.vladlin.ram_reader.domain.boundaries.CharacterInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterRepo
import ru.vladlin.ram_reader.domain.model.CharacterModel

class CharacterInteractorImpl(val characterRepo: CharacterRepo): CharacterInteractor
{
    override fun getCharacter(idCharacter: Long): Flow<CharacterModel?>
    {
        return characterRepo.getCharacter(idCharacter)
    }
}