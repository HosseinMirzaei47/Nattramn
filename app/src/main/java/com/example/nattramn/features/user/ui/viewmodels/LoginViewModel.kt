package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.user.data.UserRepository
import com.example.nattramn.features.user.data.models.AuthRequest
import com.example.nattramn.features.user.data.models.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository.getInstance(application)
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginResult = MutableLiveData<Resource<AuthResponse>>()

    fun loginUser(authRequest: AuthRequest) {
        loginResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            loginResult.postValue(userRepository.loginUser(getApplication(), authRequest))
        }
    }

}