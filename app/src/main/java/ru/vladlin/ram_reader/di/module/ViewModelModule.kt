package ru.vladlin.ram_reader.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.vladlin.ram_reader.di.key.ViewModelKey
import ru.vladlin.ram_reader.lifecycle.ViewModelFactory
import ru.vladlin.ram_reader.ui.fragmentCarousel.ViewModelCarousel
import ru.vladlin.ram_reader.ui.fragmentDetail.ViewModelDetail
import ru.vladlin.ram_reader.ui.fragmentFavorites.ViewModelFavorites
import ru.vladlin.ram_reader.ui.fragmentPaging.ViewModelPaging

@Module
abstract class ViewModelModule
{
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelPaging::class)
    abstract fun viewModelPaging(viewModelPaging: ViewModelPaging): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelCarousel::class)
    abstract fun viewModelCarousel(viewModelCarousel: ViewModelCarousel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelFavorites::class)
    abstract fun viewModelFavorites(viewModelFavorites: ViewModelFavorites): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelDetail::class)
    abstract fun viewModelDetail(viewModelDetail: ViewModelDetail): ViewModel

}