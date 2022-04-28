package ru.vladlin.ram_reader.di.component

import dagger.Subcomponent
import ru.vladlin.ram_reader.di.module.ActivityModule
import ru.vladlin.ram_reader.di.module.ViewModelModule
import ru.vladlin.ram_reader.di.scope.ActivityScope
import ru.vladlin.ram_reader.ui.MainActivity

@ActivityScope
@Subcomponent(modules = [
    ActivityModule::class,
    ViewModelModule::class
])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun feedComponent(): FeedComponent.Builder

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(module: ActivityModule):Builder
        fun build(): ActivityComponent
    }
}