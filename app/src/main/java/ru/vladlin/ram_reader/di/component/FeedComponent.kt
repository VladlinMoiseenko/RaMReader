package ru.vladlin.ram_reader.di.component

import dagger.Subcomponent
import ru.vladlin.ram_reader.di.module.FeedModule
import ru.vladlin.ram_reader.di.scope.FeedScope
import ru.vladlin.ram_reader.ui.fragmentSettings.FragmentSettings

@FeedScope
@Subcomponent(modules = [
    FeedModule::class
])
interface FeedComponent
{

    @Subcomponent.Builder
    interface Builder {

        fun build(): FeedComponent
    }

    fun inject(fragmentSettings: FragmentSettings)

}