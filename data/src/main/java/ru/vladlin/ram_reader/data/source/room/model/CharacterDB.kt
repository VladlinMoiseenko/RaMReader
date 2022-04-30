package ru.vladlin.ram_reader.data.source.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vladlin.ram_reader.data.source.room.AppDataBase.Companion.CHARACTER_TABLE_NAME

@Entity(tableName = CHARACTER_TABLE_NAME)
data class CharacterDB (
    @PrimaryKey val id: Long,
    val name: String?,
    val type: String?,
    val url: String?,
    val status: String?,
    val gender: String?,
    val created: String?,
    val image: String?,
    val species: String?
    )
