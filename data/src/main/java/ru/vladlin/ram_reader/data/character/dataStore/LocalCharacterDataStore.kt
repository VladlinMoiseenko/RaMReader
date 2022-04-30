package ru.vladlin.ram_reader.data.character.dataStore

import ru.vladlin.ram_reader.domain.model.CharacterModel

interface LocalCharacterDataStore {
    fun saveCharacter(character: CharacterModel?)
    fun characterIsSaved(idCharacter: Long): Boolean
    fun removeCharacter(idCharacter: Long)
    suspend fun getAllSavedCharacters(): List<CharacterModel>
    suspend fun getCharacter(idCharacter: Long): CharacterModel?
}