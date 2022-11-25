package com.example.barberappointments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servall.data.LoginHybridRepository
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginRepository: LoginHybridRepository
) : ViewModel() {

    private val _authState = MutableLiveData<AuthUiState>(AuthUiState.Loading)
    val authState: LiveData<AuthUiState>
        get() = _authState

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            when (val response = loginRepository.login(userName = userName, password = password)) {
                is Response.Error -> {
                    _authState.postValue(AuthUiState.Error(response.exception.message!!))
                }
                is Response.Success -> {
                    _authState.postValue(AuthUiState.Data(user = response.data))
                }
            }
        }
    }
}

sealed class AuthUiState {
    data class Error(val message: String) : AuthUiState()
    object Loading : AuthUiState()
    data class Data(val user: User?) : AuthUiState()
}