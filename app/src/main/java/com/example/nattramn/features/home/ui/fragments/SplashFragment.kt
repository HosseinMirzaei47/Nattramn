package com.example.nattramn.features.home.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.databinding.FragmentSplashBinding
import com.example.nattramn.fragments.SplashFragmentDirections

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val splashTimeOut = 1000.toLong()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_splash, container, false
        )

        Handler().postDelayed({
            Navigation.findNavController(binding.root)
                .navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }, splashTimeOut)

        return binding.root
    }

}