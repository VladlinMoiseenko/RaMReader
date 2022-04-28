package ru.vladlin.ram_reader.di.module


import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vladlin.ram_reader.themeHelper.SharedPrefManager
import ru.vladlin.ram_reader.themeHelper.SharedPrefs
import ru.vladlin.ram_reader.themeHelper.ThemeHelper
import ru.vladlin.ram_reader.themeHelper.ThemeHelperImpl

import javax.inject.Singleton

@Module
class ThemeModule(val context: Context) {

//    @Provides
//    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideSharedPrefManager(): SharedPrefManager {
        return SharedPrefManager(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(sharedPrefManager: SharedPrefManager): SharedPrefs {
        return SharedPrefs(sharedPrefManager)
    }

    @Provides
    @Singleton
    fun provideThemeHelper(sharedPrefs: SharedPrefs): ThemeHelper {
        return ThemeHelperImpl(sharedPrefs)
    }

}