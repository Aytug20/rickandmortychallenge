package com.example.rickandmortyapp.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController: NavController
    private val firstTime = "fT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        if (sharedPref.contains(firstTime))
            binding.textView.text = getString(R.string.hello)
        else {
            val editor = sharedPref.edit()
            editor.putBoolean(firstTime, true)
            editor.apply()

            binding.textView.text = getString(R.string.welcome)
        }

        val sideAnimation = AnimationUtils.loadAnimation(context, R.anim.slide)
        binding.rickLogo.startAnimation(sideAnimation)
        Looper.myLooper()?.let {
            Handler(it).postDelayed(Runnable {
                navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }, 2000)
        }
    }
}