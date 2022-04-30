package ru.vladlin.ram_reader.data.source.room.dao

import androidx.room.*
import ru.vladlin.ram_reader.data.source.room.AppDataBase.Companion.CHARACTER_TABLE_NAME
import ru.vladlin.ram_reader.data.source.room.model.CharacterDB

@Dao
interface CharacterDao
{
    @Query("SELECT * FROM ${CHARACTER_TABLE_NAME}")
    fun getAll(): List<CharacterDB>

    @Query("SELECT * FROM ${CHARACTER_TABLE_NAME} WHERE id = :id")
    fun getById(id: Long): CharacterDB?

    @Insert
    fun insert(data: CharacterDB)

    @Query("DELETE FROM ${CHARACTER_TABLE_NAME} WHERE id = :id")
    fun removeById(id: Long)
}