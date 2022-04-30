package ru.vladlin.ram_reader.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vladlin.ram_reader.di.scope.AppScope

@Module
class AppModule (val context: Context)
{
    @Provides
    @AppScope
    fun provideContext(): Context = context

}