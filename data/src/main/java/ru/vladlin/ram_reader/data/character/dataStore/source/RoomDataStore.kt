package ru.vladlin.ram_reader.data.character.dataStore.source

import ru.vladlin.ram_reader.data.character.convertUtil.toCharacterDB
import ru.vladlin.ram_reader.data.character.dataStore.LocalCharacterDataStore
import ru.vladlin.ram_reader.data.source.room.dao.CharacterDao
import ru.vladlin.ram_reader.data.source.room.dao.CharacterImageDao
import ru.vladlin.ram_reader.domain.model.CharacterModel

class RoomDataStore(
    val characterImageDao: CharacterImageDao,
    val characterDao: CharacterDao
    ) : LocalCharacterDataStore {

    override fun saveCharacter(character: CharacterModel?) {
        character?.let {
            val characterDB = it.toCharacterDB()
            characterImageDao.insert(
                characterDB,
            )
        }
    }

    override fun characterIsSaved(idCharacter: Long): Boolean {
        return characterDao.getById(idCharacter) != null
    }

    override fun removeCharacter(idCharacter: Long) {
        characterDao.removeById(idCharacter)
    }

    override suspend fun getAllSavedCharacters(): List<CharacterModel> {
        return characterImageDao.getAll()
    }

    override suspend fun getCharacter(idCharacter: Long): CharacterModel {
        return characterImageDao.get(idCharacter)
    }

}