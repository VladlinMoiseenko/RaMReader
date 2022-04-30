package ru.vladlin.ram_reader.ui

import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import kotlinx.parcelize.Parcelize
import ru.vladlin.ram_reader.ui.fragmentDetail.FragmentDetail
import ru.vladlin.ram_reader.ui.fragmentFavorites.FragmentFavorites
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
    class Favorites : AppScreen("FragmentFavorites") {
        override fun create() = FragmentFavorites()
    }

    @Parcelize
    class Detail(private val id_: Long) : AppScreen("FragmentDetail") {
        override fun create() = FragmentDetail.create(id_)
    }

    fun MultiStack() = MultiAppScreen(
        "MultiStack",
        listOf(List(), Favorites(), Settings()),
        0
    )

}