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

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository.getInstance(application)
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    val registerResult = MutableLiveData<Resource<AuthResponse>>()

    fun registerUser(authRequest: AuthRequest) {
        registerResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            registerResult.postValue(userRepository.registerUser(getApplication(), authRequest))
        }
    }

}