package ru.vladlin.ram_reader.di.module

import dagger.Module
import dagger.Provides
import ru.vladlin.ram_reader.data.character.dataStore.LocalCharacterDataStore
import ru.vladlin.ram_reader.data.character.dataStore.NetCharacterDataStore
import ru.vladlin.ram_reader.data.character.dataStore.source.RetrofitDataStore
import ru.vladlin.ram_reader.data.character.dataStore.source.RoomDataStore
import ru.vladlin.ram_reader.data.character.repo.CharacterRepoImpl
import ru.vladlin.ram_reader.data.source.retrofit.ApiService
import ru.vladlin.ram_reader.data.source.room.dao.CharacterDao
import ru.vladlin.ram_reader.data.source.room.dao.CharacterImageDao
import ru.vladlin.ram_reader.di.scope.FeedScope
import ru.vladlin.ram_reader.domain.boundaries.CharacterInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterLocalInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterNetworkInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterRepo
import ru.vladlin.ram_reader.domain.interactor.CharacterInteractorImpl
import ru.vladlin.ram_reader.domain.interactor.CharacterLocalInteractorImpl
import ru.vladlin.ram_reader.domain.interactor.CharacterNetworkInteractorImpl


@Module
class FeedModule
{

    @Provides
    @FeedScope
    fun provideCharacterDataStore(apiService: ApiService): NetCharacterDataStore
            = RetrofitDataStore(apiService)

    @Provides
    @FeedScope
    fun provideLocalDataStore(characterImageDao: CharacterImageDao,
                              characterDao: CharacterDao): LocalCharacterDataStore
            = RoomDataStore(characterImageDao, characterDao)

    @Provides
    @FeedScope
    fun provideCharacterRepository(localCharacterDataStore: LocalCharacterDataStore,
                                   netCharacterDataStore: NetCharacterDataStore): CharacterRepo
            = CharacterRepoImpl(localCharacterDataStore, netCharacterDataStore)

    @Provides
    @FeedScope
    fun provideCharacterLocalInteractor(characterRepo: CharacterRepo): CharacterLocalInteractor
            = CharacterLocalInteractorImpl(characterRepo)

    @Provides
    @FeedScope
    fun provideCharacterFragmentInteractor(characterRepo: CharacterRepo): CharacterInteractor
            = CharacterInteractorImpl(characterRepo)

    @Provides
    @FeedScope
    fun provideCharacterNetworkInteractor(characterRepo: CharacterRepo): CharacterNetworkInteractor
            = CharacterNetworkInteractorImpl(characterRepo)


}