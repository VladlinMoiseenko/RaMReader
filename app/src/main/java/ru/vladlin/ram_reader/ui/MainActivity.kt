package ru.vladlin.ram_reader.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.airbnb.lottie.LottieAnimationView
import com.github.terrakok.modo.android.ModoRender
import com.github.terrakok.modo.android.init
import com.github.terrakok.modo.android.multi.TabViewFactory
import com.github.terrakok.modo.android.saveState
import com.github.terrakok.modo.back
import com.github.terrakok.modo.forward
import com.github.terrakok.modo.newStack
import com.github.terrakok.modo.selectStack
import ru.vladlin.ram_reader.App
import ru.vladlin.ram_reader.App.Companion.themeHelper
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.di.module.ActivityModule

class MainActivity : AppCompatActivity(), TabViewFactory {
    private val modo = App.modo

    private val modoRender by lazy {
        object : ModoRender(this@MainActivity, R.id.container) {
        }
    }

    val activitySubComponent by lazy {
        return@lazy App.DI.appComponent
            .activityComponent()
            .activityModule(ActivityModule(this))
            .build()
    }

    val feedComponent by lazy {
        return@lazy activitySubComponent.feedComponent().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activitySubComponent.inject(this)

        AppCompatDelegate.setDefaultNightMode(themeHelper.getNightMode())

        setTheme(themeHelper.getAppTheme())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //modo.init(savedInstanceState, Screens.MultiStack())
        //modo.newStack(Screens.Splash())
        modo.forward(Screens.Splash())
    }

    override fun onResume() {
        super.onResume()
        modo.render = modoRender
    }

    override fun onPause() {
        modo.render = null
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        modo.saveState(outState)
    }

    override fun onBackPressed() {
        modo.back()
    }

    override fun createTabView(index: Int, parent: LinearLayout): View =
        LayoutInflater.from(this)
            .inflate(R.layout.layout_tab, parent, false)
            .apply {
                val anim: LottieAnimationView = findViewById(R.id.animationView)
                when (index) {
                    0 -> anim.setAnimation("lottie_carousel.json")
                    1 -> anim.setAnimation("lottie_list.json")
                    2 -> anim.setAnimation("lottie_favorites.json")
                    3 -> anim.setAnimation("lottie_settings.json")
                    else -> anim.setAnimation("lottie_list.json")
                }
                findViewById<TextView>(R.id.title).text = when (index) {
                    0 -> resources.getString(R.string.carousel)
                    1 -> resources.getString(R.string.list)
                    2 -> resources.getString(R.string.favorites)
                    3 -> resources.getString(R.string.settings)
                    else -> resources.getString(R.string.list)
                }
                setOnClickListener {
                    anim.playAnimation()
                    modo.selectStack(index)
                }
            }
}