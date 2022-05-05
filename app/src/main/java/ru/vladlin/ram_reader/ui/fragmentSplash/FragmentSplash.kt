package ru.vladlin.ram_reader.ui.fragmentSplash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.modo.android.init
import ru.vladlin.ram_reader.App.Companion.modo
import ru.vladlin.ram_reader.databinding.FragmentSplashBinding
import ru.vladlin.ram_reader.ui.Screens

class FragmentSplash : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.splashAnimation.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                modo.init(savedInstanceState, Screens.MultiStack())
                //modo.init(null, Screens.MultiStack())
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}
        })

        return view
    }
}