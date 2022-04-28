package ru.vladlin.ram_reader

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.MultiReducer
import com.github.terrakok.modo.android.AppReducer
import com.github.terrakok.modo.android.LogReducer
import ru.vladlin.ram_reader.di.component.AppComponent
import ru.vladlin.ram_reader.di.component.DaggerAppComponent
import ru.vladlin.ram_reader.di.module.AppModule
import ru.vladlin.ram_reader.themeHelper.SharedPrefManager
import ru.vladlin.ram_reader.themeHelper.SharedPrefs
import ru.vladlin.ram_reader.themeHelper.ThemeHelper
import ru.vladlin.ram_reader.themeHelper.ThemeHelperImpl
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var themeHelper: ThemeHelper

    companion object {
        lateinit var modo: Modo private set
    }

    object DI
    {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        modo = Modo(LogReducer(AppReducer(this, MultiReducer())))
        super.onCreate()

        DI.appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()

        AppCompatDelegate.setDefaultNightMode(themeHelper.getNightMode())
    }
}