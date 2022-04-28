package ru.vladlin.ram_reader.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.github.terrakok.modo.android.ModoRender
import com.github.terrakok.modo.android.init
import com.github.terrakok.modo.android.multi.TabViewFactory
import com.github.terrakok.modo.android.saveState
import com.github.terrakok.modo.back
import com.github.terrakok.modo.selectStack
import ru.vladlin.ram_reader.App
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.di.module.ActivityModule
import ru.vladlin.ram_reader.themeHelper.ThemeHelper
import javax.inject.Inject

class MainActivity : AppCompatActivity(), TabViewFactory {
    private val modo = App.modo

    @Inject
    lateinit var themeHelper: ThemeHelper

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

        setTheme(themeHelper.getAppTheme())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        modo.init(savedInstanceState, Screens.MultiStack())
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
                val anim: LottieAnimationView = findViewById<LottieAnimationView>(R.id.animationView)
                anim.setAnimation("tab${index}.json")
                findViewById<TextView>(R.id.title).text = "Tab ${index + 1}"
                setOnClickListener {
                    anim.playAnimation()
                    modo.selectStack(index)
                }
            }
}