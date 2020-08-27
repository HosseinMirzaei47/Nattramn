package com.example.nattramn.fragments

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

class SplashFragment : Fragment() {

    private val splashTimeOut = 1000.toLong()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSplashBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_splash, container, false
        )

        Handler().postDelayed({
            /*findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment())*/
            Navigation.findNavController(binding.root)
                .navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }, splashTimeOut)

        return binding.root
    }

}