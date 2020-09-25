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
import com.example.nattramn.core.snackMaker
import com.example.nattramn.databinding.FragmentRegisterBinding
import com.example.nattramn.features.user.data.UserNetwork
import com.example.nattramn.features.user.data.models.AuthRequest
import com.example.nattramn.features.user.ui.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding = FragmentRegisterBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = registerViewModel
            registerRequest = AuthRequest(
                UserNetwork(
                    email = registerViewModel.email.value,
                    username = registerViewModel.username.value,
                    password = registerViewModel.password.value
                )
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onRegisterClick()

        setOnClicks()

    }

    private fun onRegisterClick() {
        registerViewModel.registerResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    snackMaker(requireView(), "ثبت نام با موفقیت انجام شد")
                    Navigation.findNavController(requireView())
                        .navigate(RegisterFragmentDirections.actionRegisterFragmentToHomerFragment())
                }
                Status.LOADING -> {

                }
                else -> {
                    snackMaker(requireView(), "خطا در ارتباط با سرور")
                }
            }
        })

        registerViewModel.username.observe(viewLifecycleOwner, Observer { username ->
            binding.registerRequest = AuthRequest(
                UserNetwork(
                    email = registerViewModel.email.value,
                    username = username,
                    password = registerViewModel.password.value
                )
            )
        })

        registerViewModel.email.observe(viewLifecycleOwner, Observer { email ->
            binding.registerRequest = AuthRequest(
                UserNetwork(
                    email = email,
                    username = registerViewModel.username.value,
                    password = registerViewModel.password.value
                )
            )
        })

        registerViewModel.password.observe(viewLifecycleOwner, Observer { password ->
            binding.registerRequest = AuthRequest(
                UserNetwork(
                    email = registerViewModel.email.value,
                    username = registerViewModel.username.value,
                    password = password
                )
            )
        })
    }

    private fun setOnClicks() {

        binding.tvEnter.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        /*binding.btnMembership.setOnClickListener { view ->
            if (binding.registerUsername.text.isValidUsername() && binding.registerUsernameConfirm.text.isValidEmail()) {

                Navigation.findNavController(view)
                    .navigate(RegisterFragmentDirections.actionRegisterFragmentToHomerFragment())

            }
        }*/

    }

    private fun CharSequence?.isValidUsername(): Boolean {

        if (this == null) {

            binding.registerUsername.requestFocus()
            binding.registerUsername.error = getString(R.string.errorEnterUsername)
            return false

        }

        if (isNullOrEmpty()) {

            binding.registerUsername.requestFocus()
            binding.registerUsername.error = getString(R.string.errorEnterUsername)
            return false

        } else if (binding.registerUsername.text!!.length > LoginFragment.minUsernameLength
            && !Patterns.EMAIL_ADDRESS.matcher(this).matches()
        ) {

            return true

        } else if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {

            binding.registerUsername.requestFocus()
            binding.registerUsername.error = getString(R.string.errorEmailOrUsernameFormat)
            return false

        }

        binding.registerUsername.error = null
        return true
    }

    private fun CharSequence?.isValidEmail(): Boolean {

        if (isNullOrEmpty()) {
            binding.registerUsernameConfirm.requestFocus()
            binding.registerUsernameConfirm.error = getString(R.string.errorEnterEmail)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(this!!).matches()) {
            binding.registerUsernameConfirm.requestFocus()
            binding.registerUsernameConfirm.error = getString(R.string.errorEmailFormat)
            return false
        }

        return true
    }

}