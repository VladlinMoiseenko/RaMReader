package ru.vladlin.ram_reader.domain.boundaries

import kotlinx.coroutines.flow.Flow
import ru.vladlin.ram_reader.domain.model.CharacterModel

interface CharacterInteractor
{
    fun getCharacter(idCharacter: Long): Flow<CharacterModel?>
}