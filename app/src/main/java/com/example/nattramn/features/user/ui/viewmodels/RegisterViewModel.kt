package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.user.data.AuthRepository
import com.example.nattramn.features.user.data.UserNetwork
import com.example.nattramn.features.user.data.models.AuthRequest
import com.example.nattramn.features.user.data.models.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = AuthRepository.getInstance(application)
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    private val _registerResult = MutableLiveData<Resource<AuthResponse>>()
    val registerResult: LiveData<Resource<AuthResponse>> get() = _registerResult

    fun registerUser(
        username: String,
        email: String,
        password: String
    ) {

        val authRequest =
            AuthRequest(UserNetwork(username = username, email = email, password = password))

        _registerResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _registerResult.postValue(userRepository.registerUser(getApplication(), authRequest))
        }
    }

}