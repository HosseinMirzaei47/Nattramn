package com.example.nattramn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nattramn.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonOnClicks()

    }

    private fun buttonOnClicks() {

        loginEnterButton.setOnClickListener {
            val homeFragment = HomeFragment()

            fragmentManager?.beginTransaction()
                ?.replace(R.id.loginFrame, homeFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        loginRegisterButton.setOnClickListener {

            val registerFragment = RegisterFragment()

            fragmentManager?.beginTransaction()
                ?.replace(R.id.loginFrame, registerFragment)
                ?.addToBackStack(null)
                ?.commit()

        }
    }
}