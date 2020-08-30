package com.example.nattramn.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.databinding.FragmentRegisterBinding
import com.example.nattramn.viewModels.RegisterViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClicks()

    }

    private fun setOnClicks() {

        binding.tvEnter.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding.btnMembership.setOnClickListener { view ->
            if (binding.registerUsername.text.isValidUsername() && binding.registerUsernameConfirm.text.isValidEmail()) {

                Navigation.findNavController(view)
                    .navigate(RegisterFragmentDirections.actionRegisterFragmentToHomerFragment())

            }
        }

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