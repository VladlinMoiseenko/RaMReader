package ru.vladlin.ram_reader.di.component

import android.content.Context
import dagger.Component
import ru.vladlin.ram_reader.di.scope.AppScope
import ru.vladlin.ram_reader.App
import ru.vladlin.ram_reader.di.module.AppModule
import ru.vladlin.ram_reader.di.module.ThemeModule

@AppScope
@Component(modules = [
    AppModule::class,
    ThemeModule::class,
//    RetrofitModule::class,
//    RoomModule::class,
//    NetworkModule::class
])

interface AppComponent
{
    fun context(): Context

//    fun apiService(): ApiService
//    fun characterDao(): CharacterDao
//    fun networkInteractor(): NetworkErrorInteractor
    fun inject(app: App)

    fun activityComponent(): ActivityComponent.Builder
}