package ru.vladlin.ram_reader.themeHelper

import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val spm: SharedPrefManager
) {
    fun setDarkModeSetting(darkMode: Int) {
        spm.setIntValue(SHARED_PREF_DARK_MODE, darkMode)
    }

    fun getDarkModeSetting() = spm.getIntValue(SHARED_PREF_DARK_MODE)

    fun setThemeSetting(darkMode: Int) {
        spm.setIntValue(SHARED_PREF_THEME, darkMode)
    }

    fun getThemeSetting() = spm.getIntValue(SHARED_PREF_THEME)

    companion object {
        const val SHARED_PREF_DARK_MODE = "SHARED_PREF_WATCH_PROVIDER_REGION"
        const val SHARED_PREF_THEME = "SHARED_PREF_THEME"
    }
}
