package ru.vladlin.ram_reader.data.character.convertUtil

import ru.vladlin.ram_reader.data.source.room.model.CharacterDB
import ru.vladlin.ram_reader.domain.model.CharacterModel

fun CharacterModel.toCharacterDB(): CharacterDB = CharacterDB(id, name, type, url, status, gender, created, image, species)
fun CharacterDB.toCharacter(): CharacterModel = CharacterModel(id, name, type, url, status, gender, created, image, species)
