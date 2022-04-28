package ru.vladlin.ram_reader.ui

import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import kotlinx.parcelize.Parcelize
import ru.vladlin.ram_reader.ui.fragmentSettings.FragmentSettings

object Screens {


    @Parcelize
    class Settings : AppScreen("FragmentSettings") {
        override fun create() = FragmentSettings()
    }

    fun MultiStack() = MultiAppScreen(
        "MultiStack",
        listOf(Settings()),
        0
    )

}