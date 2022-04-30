package ru.vladlin.ram_reader.ui.fragmentSettings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ru.vladlin.ram_reader.App.Companion.themeHelper
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.ui.MainActivity

class FragmentSettings : PreferenceFragmentCompat() {
    private var darkModePref: ListPreference? = null
    private var themePref: ListPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        darkModePref = (findPreference("dark_mode_key") as? ListPreference)
        themePref = (findPreference("theme_key") as? ListPreference)

        displayPrefs()
    }

    private fun displayPrefs() {
        darkModePref?.summary = resources.getStringArray(R.array.night_mode_items)[themeHelper.getDarkModeSetting()]

        darkModePref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val value = (newValue as String).toIntOrNull() ?: return@OnPreferenceChangeListener false
                AppCompatDelegate.setDefaultNightMode(themeHelper.getNightMode(value))
                themeHelper.setDarkModeSetting(value)
                darkModePref?.summary = resources.getStringArray(R.array.night_mode_items)[value]
                true
            }

        themePref?.summary = resources.getStringArray(R.array.theme_items)[themeHelper.getThemeSetting()]

        themePref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val themeSetting = (newValue as String).toIntOrNull() ?: return@OnPreferenceChangeListener false
                themeHelper.setThemeSetting(themeSetting)
                requireActivity().recreate()
                true
            }
    }
}