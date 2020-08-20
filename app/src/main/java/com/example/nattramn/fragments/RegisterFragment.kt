package com.example.nattramn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nattramn.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClicks()

    }

    private fun setOnClicks() {

        tv_enter.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        btn_membership.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        }

    }
}