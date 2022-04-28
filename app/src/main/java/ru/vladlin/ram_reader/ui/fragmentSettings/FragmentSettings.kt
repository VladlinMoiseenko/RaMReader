package ru.vladlin.ram_reader.ui.fragmentSettings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ru.vladlin.ram_reader.App
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.themeHelper.SharedPrefs
import ru.vladlin.ram_reader.themeHelper.ThemeHelper
import ru.vladlin.ram_reader.ui.MainActivity
import javax.inject.Inject

class FragmentSettings : PreferenceFragmentCompat() {
    private var darkModePref: ListPreference? = null
    private var themePref: ListPreference? = null

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    //val sharedPrefs: SharedPrefs = App.sharedPrefs

    @Inject
    lateinit var themeHelper: ThemeHelper
    //val themeHelper: ThemeHelper = App.themeHelper


    val fragmentListComponent by lazy {
        return@lazy (requireActivity() as MainActivity).feedComponent
    }

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
        darkModePref?.summary = resources.getStringArray(R.array.night_mode_items)[sharedPrefs.getDarkModeSetting()]

        darkModePref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val value = (newValue as String).toIntOrNull() ?: return@OnPreferenceChangeListener false
                AppCompatDelegate.setDefaultNightMode(themeHelper.getNightMode(value))
                sharedPrefs.setDarkModeSetting(value)
                darkModePref?.summary = resources.getStringArray(R.array.night_mode_items)[value]
                true
            }

        themePref?.summary = resources.getStringArray(R.array.theme_items)[sharedPrefs.getThemeSetting()]

        themePref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val themeSetting = (newValue as String).toIntOrNull() ?: return@OnPreferenceChangeListener false
                sharedPrefs.setThemeSetting(themeSetting)
                requireActivity().recreate()
                true
            }
    }
}