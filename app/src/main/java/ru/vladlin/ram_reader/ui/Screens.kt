package ru.vladlin.ram_reader.ui

import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import kotlinx.parcelize.Parcelize
import ru.vladlin.ram_reader.ui.fragmentList.FragmentList
import ru.vladlin.ram_reader.ui.fragmentSettings.FragmentSettings

object Screens {

    @Parcelize
    class List : AppScreen("FragmentList") {
        override fun create() = FragmentList()
    }

    @Parcelize
    class Settings : AppScreen("FragmentSettings") {
        override fun create() = FragmentSettings()
    }

    @Parcelize
    class Fo : AppScreen("FragmentFo") {
        override fun create() = FragmentFo()
    }

    fun MultiStack() = MultiAppScreen(
        "MultiStack",
        listOf(List(), Fo(), Settings()),
        0
    )

}