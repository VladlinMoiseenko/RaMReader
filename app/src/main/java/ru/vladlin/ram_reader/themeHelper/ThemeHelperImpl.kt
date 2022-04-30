package ru.vladlin.ram_reader.themeHelper

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import ru.vladlin.ram_reader.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeHelperImpl @Inject constructor(
    private val context: Context
) : ThemeHelper {

    override fun getAppTheme() = when (getThemeSetting()) {
        0 -> R.style.AppTheme_Blue
        1 -> R.style.AppTheme_Red
        2 -> R.style.AppTheme_Green
        else -> R.style.AppTheme_Blue
    }

    override fun getNightMode(darkModeSetting: Int): Int {
        val result = if (darkModeSetting == SAVED_NIGHT_VALUE) {
            getDarkModeSetting()
        } else {
            darkModeSetting
        }

        return when (result) {
            0 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            1 -> AppCompatDelegate.MODE_NIGHT_NO
            2 -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
        }
    }

    override fun getThemeSetting(): Int {
        return getIntValue(SHARED_PREF_THEME)
    }

    override fun getDarkModeSetting(): Int {
        return getIntValue(SHARED_PREF_DARK_MODE)
    }

    override fun setThemeSetting(darkMode: Int) {
        setIntValue(SHARED_PREF_THEME, darkMode)
    }

    override fun setDarkModeSetting(darkMode: Int) {
        setIntValue(SHARED_PREF_DARK_MODE, darkMode)
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStringValue(key: String, default: String? = null) =
        sharedPreferences.getString(key, default) ?: default

    fun setStringValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getLongValue(key: String, default: Long = 0L) =
        sharedPreferences.getLong(key, default)

    fun setLongValue(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getIntValue(key: String, default: Int = 0) =
        sharedPreferences.getInt(key, default)

    fun setIntValue(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getBoolValue(key: String, default: Boolean = false) =
        sharedPreferences.getBoolean(key, default)

    fun setBoolValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }


    companion object {
        const val SAVED_NIGHT_VALUE = 1234

        const val SHARED_PREF_DARK_MODE = "SHARED_PREF_WATCH_PROVIDER_REGION"
        const val SHARED_PREF_THEME = "SHARED_PREF_THEME"
    }
}