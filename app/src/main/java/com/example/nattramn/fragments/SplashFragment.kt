package com.example.nattramn.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nattramn.R

class SplashFragment : Fragment() {

    private val splashTimeOut = 1000.toLong()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_splash, container, false)

        Handler().postDelayed({
            /*findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment())*/
            Navigation.findNavController(view)
                .navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }, splashTimeOut)

        return view
    }

}