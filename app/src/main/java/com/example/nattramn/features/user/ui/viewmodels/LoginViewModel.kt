package com.example.nattramn.features.user.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nattramn.features.user.data.LoginRequest
import com.example.nattramn.features.user.data.UserRepository

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository.getInstance()

    suspend fun loginUser(loginRequest: LoginRequest): Boolean {
        return userRepository.loginUser(loginRequest)
    }

}