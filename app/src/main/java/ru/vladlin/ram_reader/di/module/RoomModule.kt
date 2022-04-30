package ru.vladlin.ram_reader.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.vladlin.ram_reader.data.source.room.AppDataBase
import ru.vladlin.ram_reader.data.source.room.dao.CharacterDao
import ru.vladlin.ram_reader.data.source.room.dao.CharacterImageDao
import ru.vladlin.ram_reader.di.scope.AppScope

@Module
class RoomModule
{
    @Provides
    @AppScope
    fun provideAppDatabase(context: Context): AppDataBase
    = Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.DB_NAME).fallbackToDestructiveMigration().build()

    @Provides
    @AppScope
    fun provideCharacterDao(appDataBase: AppDataBase): CharacterDao
    = appDataBase.characterDao()

    @Provides
    @AppScope
    fun provideCharacterImageDao(appDataBase: AppDataBase): CharacterImageDao
    = appDataBase.characterImageDao()

}