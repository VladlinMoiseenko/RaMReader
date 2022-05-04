package ru.vladlin.ram_reader.ui

import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import kotlinx.parcelize.Parcelize
import ru.vladlin.ram_reader.ui.fragmentCarousel.FragmentCarousel
import ru.vladlin.ram_reader.ui.fragmentDetail.FragmentDetail
import ru.vladlin.ram_reader.ui.fragmentFavorites.FragmentFavorites
import ru.vladlin.ram_reader.ui.fragmentPaging.FragmentPaging
import ru.vladlin.ram_reader.ui.fragmentSettings.FragmentSettings

object Screens {

    @Parcelize
    class Paging : AppScreen("FragmentPaging") {
        override fun create() = FragmentPaging()
    }

    @Parcelize
    class Carousel : AppScreen("FragmentCarousel") {
        override fun create() = FragmentCarousel()
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
        listOf(Carousel(), Paging(), Favorites(), Settings()),
        0
    )

}