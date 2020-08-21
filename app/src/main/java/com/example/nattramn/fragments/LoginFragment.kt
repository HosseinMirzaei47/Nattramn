package com.example.nattramn.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nattramn.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    companion object {
        private const val minUsernameLength = 7
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSystemUI()

        buttonOnClicks()

    }

    private fun buttonOnClicks() {

        loginEnterButton.setOnClickListener { view ->

            if (loginUsername.text!!.isValidUsername()) {
                Navigation.findNavController(view)
                    .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }

        }

        loginRegisterButton.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun CharSequence.isValidUsername(): Boolean {

        if (isNullOrEmpty()) {

            loginUsername.requestFocus()
            loginUsername.error = "نام کاربری خود را وارد نمایید"
            return false

        } else if (loginUsername.text!!.length > minUsernameLength
            && !Patterns.EMAIL_ADDRESS.matcher(this).matches()
        ) {

            return true

        } else if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {

            loginUsername.requestFocus()
            loginUsername.error = "نام کاربری یا ایمیل وارد شده صحیح نیست"
            return false

        }

        loginUsername.error = null
        return true
    }


    private fun showSystemUI() {
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                /*or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN*/)
    }

}