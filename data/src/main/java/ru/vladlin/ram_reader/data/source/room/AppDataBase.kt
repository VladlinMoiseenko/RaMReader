package ru.vladlin.ram_reader.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vladlin.ram_reader.data.source.room.AppDataBase.Companion.CURRENT_VERISON
import ru.vladlin.ram_reader.data.source.room.dao.CharacterDao
import ru.vladlin.ram_reader.data.source.room.dao.CharacterImageDao
import ru.vladlin.ram_reader.data.source.room.model.CharacterDB

@Database(entities = arrayOf(CharacterDB::class), version = CURRENT_VERISON)
abstract class AppDataBase: RoomDatabase()
{
    companion object {
        const val CURRENT_VERISON = 1
        const val DB_NAME = "RM_DB.sql"

        const val CHARACTER_TABLE_NAME = "CHARACTER_TABLE"
        const val IMAGE_TABLE_NAME = "IMAGE_TABLE"
    }

    abstract fun characterDao(): CharacterDao
    
    abstract fun characterImageDao(): CharacterImageDao
}