package ru.vladlin.ram_reader.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.vladlin.ram_reader.di.key.ViewModelKey
import ru.vladlin.ram_reader.lifecycle.ViewModelFactory
import ru.vladlin.ram_reader.ui.fragmentList.ViewModelList

@Module
abstract class ViewModelModule
{
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelList::class)
    abstract fun viewModelList(viewModelList: ViewModelList): ViewModel


}