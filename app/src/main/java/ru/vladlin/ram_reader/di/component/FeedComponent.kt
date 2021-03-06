package ru.vladlin.ram_reader.di.component

import dagger.Subcomponent
import ru.vladlin.ram_reader.di.module.FeedModule
import ru.vladlin.ram_reader.di.scope.FeedScope
import ru.vladlin.ram_reader.ui.fragmentCarousel.FragmentCarousel
import ru.vladlin.ram_reader.ui.fragmentDetail.FragmentDetail
import ru.vladlin.ram_reader.ui.fragmentFavorites.FragmentFavorites
import ru.vladlin.ram_reader.ui.fragmentPaging.FragmentPaging
import ru.vladlin.ram_reader.ui.fragmentSettings.FragmentSettings
import ru.vladlin.ram_reader.ui.fragmentSplash.FragmentSplash

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

    fun inject(fragmentCarousel: FragmentCarousel)
    fun inject(fragmentPaging: FragmentPaging)
    fun inject(fragmentSettings: FragmentSettings)
    fun inject(fragmentFavorites: FragmentFavorites)
    fun inject(fragmentDetail: FragmentDetail)
    fun inject(fragmentSplash: FragmentSplash)
}