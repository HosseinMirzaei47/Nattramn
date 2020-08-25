package com.example.nattramn.fragments

import android.os.Bundle
import android.util.Patterns
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
            if (registerUsername.text.isValidUsername() && registerUsernameConfirm.text.isValidEmail()) {

                Navigation.findNavController(view)
                    .navigate(RegisterFragmentDirections.actionRegisterFragmentToHomerFragment())

            }
        }

    }

    private fun CharSequence?.isValidUsername(): Boolean {

        if (this == null) {

            registerUsername.requestFocus()
            registerUsername.error = getString(R.string.errorEnterUsername)
            return false

        }

        if (isNullOrEmpty()) {

            registerUsername.requestFocus()
            registerUsername.error = getString(R.string.errorEnterUsername)
            return false

        } else if (registerUsername.text!!.length > LoginFragment.minUsernameLength
            && !Patterns.EMAIL_ADDRESS.matcher(this).matches()
        ) {

            return true

        } else if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {

            registerUsername.requestFocus()
            registerUsername.error = getString(R.string.errorEmailOrUsernameFormat)
            return false

        }

        registerUsername.error = null
        return true
    }

    private fun CharSequence?.isValidEmail(): Boolean {

        if (isNullOrEmpty()) {
            registerUsernameConfirm.requestFocus()
            registerUsernameConfirm.error = getString(R.string.errorEnterEmail)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
            registerUsernameConfirm.requestFocus()
            registerUsernameConfirm.error = getString(R.string.errorEmailFormat)
            return false
        }

        return true
    }


}