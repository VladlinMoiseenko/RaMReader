package ru.vladlin.ram_reader.themeHelper

import ru.vladlin.ram_reader.themeHelper.ThemeHelperImpl.Companion.SAVED_NIGHT_VALUE

interface ThemeHelper {
    fun getAppTheme(): Int
    fun getNightMode(darkModeSetting: Int = SAVED_NIGHT_VALUE): Int

    fun getThemeSetting(): Int
    fun getDarkModeSetting(): Int
    fun setThemeSetting(darkMode: Int)
    fun setDarkModeSetting(darkMode: Int)
}