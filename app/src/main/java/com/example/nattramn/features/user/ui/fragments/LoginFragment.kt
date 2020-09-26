package com.example.nattramn.features.user.ui.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.core.resource.Status
import com.example.nattramn.databinding.FragmentLoginBinding
import com.example.nattramn.features.user.data.UserNetwork
import com.example.nattramn.features.user.data.models.AuthRequest
import com.example.nattramn.features.user.ui.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    companion object {
        const val minUsernameLength = 7
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showSystemUI()

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding = FragmentLoginBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = loginViewModel
            loginRequest = AuthRequest(
                UserNetwork(
                    email = loginViewModel.email.value,
                    password = loginViewModel.password.value
                )
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onEnterClick()

        onRegisterClick()

    }

    private fun onEnterClick() {

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                Navigation.findNavController(requireView())
                    .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        })

        loginViewModel.email.observe(
            viewLifecycleOwner,
            Observer { username ->
                binding.loginRequest = AuthRequest(
                    UserNetwork(
                        email = username,
                        password = loginViewModel.password.value
                    )
                )
            })

        loginViewModel.password.observe(
            viewLifecycleOwner,
            Observer { password ->
                binding.loginRequest = AuthRequest(
                    UserNetwork(
                        email = loginViewModel.email.value,
                        password = password
                    )
                )
            })
    }

    private fun onRegisterClick() {

        binding.loginRegisterButton.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

    }

    private fun CharSequence?.isValidUsername(): Boolean {

        if (this == null) {

            binding.loginUsername.requestFocus()
            binding.loginUsername.error = getString(R.string.errorEnterUsername)
            return false

        }

        if (isNullOrEmpty()) {

            binding.loginUsername.requestFocus()
            binding.loginUsername.error = getString(R.string.errorEnterUsername)
            return false

        } else if (binding.loginUsername.text!!.length > minUsernameLength
            && !Patterns.EMAIL_ADDRESS.matcher(this).matches()
        ) {

            return true

        } else if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {

            binding.loginUsername.requestFocus()
            binding.loginUsername.error = getString(R.string.errorEmailOrUsernameFormat)
            return false

        }

        binding.loginUsername.error = null
        return true
    }

    private fun showSystemUI() {
        @Suppress("DEPRECATION")
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

}
