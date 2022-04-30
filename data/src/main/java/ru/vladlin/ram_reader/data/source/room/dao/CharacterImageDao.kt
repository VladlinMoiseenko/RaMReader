package ru.vladlin.ram_reader.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.vladlin.ram_reader.data.source.room.AppDataBase.Companion.CHARACTER_TABLE_NAME
import ru.vladlin.ram_reader.data.source.room.model.CharacterDB
import ru.vladlin.ram_reader.domain.model.CharacterModel

@Dao
public abstract class CharacterImageDao()
{
    @Insert
    abstract fun insertCharacter(data: CharacterDB)
    
    @Transaction
    open fun insert(characterDB: CharacterDB)
    {
        insertCharacter(characterDB)
    }
    
    @Transaction
    @Query("SELECT * from ${CHARACTER_TABLE_NAME} WHERE id = :id")
    abstract fun get(id: Long): CharacterModel
    
    @Transaction
    @Query("SELECT * from ${CHARACTER_TABLE_NAME}")
    abstract fun getAll(): List<CharacterModel>
}