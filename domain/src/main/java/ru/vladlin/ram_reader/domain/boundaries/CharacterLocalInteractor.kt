package ru.vladlin.ram_reader.domain.boundaries

import kotlinx.coroutines.flow.Flow
import ru.vladlin.ram_reader.domain.model.CharacterModel

interface CharacterLocalInteractor
{
    fun getAllLocalCharacters(): Flow<List<CharacterModel>>
    fun saveCharacter(idCharacter: Long): Flow<Boolean>
    fun isSavedCharacter(idCharacter: Long): Flow<Boolean>
}