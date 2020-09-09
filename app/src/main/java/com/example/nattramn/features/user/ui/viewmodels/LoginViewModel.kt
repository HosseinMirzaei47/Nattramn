package com.example.nattramn.features.user.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.features.user.data.LoginRequest
import com.example.nattramn.features.user.data.UserRepository

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository.getInstance()

    suspend fun loginUser(context: Context, loginRequest: LoginRequest) {
        if (NetworkHelper.isOnline(context)) {
            userRepository.loginUser()
        }
    }

}