package ru.vladlin.ram_reader.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vladlin.ram_reader.di.scope.AppScope
import ru.vladlin.ram_reader.themeHelper.SharedPrefManager
import ru.vladlin.ram_reader.themeHelper.SharedPrefs
import ru.vladlin.ram_reader.themeHelper.ThemeHelper
import ru.vladlin.ram_reader.themeHelper.ThemeHelperImpl
import javax.inject.Singleton

@Module
class AppModule (val context: Context)
{
    @Provides
    @AppScope
    fun provideContext(): Context = context

}