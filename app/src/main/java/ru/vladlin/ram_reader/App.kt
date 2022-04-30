package ru.vladlin.ram_reader

import android.app.Application
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.MultiReducer
import com.github.terrakok.modo.android.AppReducer
import com.github.terrakok.modo.android.LogReducer
import ru.vladlin.ram_reader.di.component.AppComponent
import ru.vladlin.ram_reader.di.component.DaggerAppComponent
import ru.vladlin.ram_reader.di.module.AppModule
import ru.vladlin.ram_reader.themeHelper.ThemeHelper
import ru.vladlin.ram_reader.themeHelper.ThemeHelperImpl

class App : Application() {

    object DI {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        modo = Modo(LogReducer(AppReducer(this, MultiReducer())))
        super.onCreate()

        themeHelper = ThemeHelperImpl(this)

        DI.appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

    companion object {
        lateinit var modo: Modo private set
        lateinit var themeHelper: ThemeHelper private set
    }
}