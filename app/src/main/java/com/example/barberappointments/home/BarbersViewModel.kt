package com.example.barberappointments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servall.data.remote.BarbersRemoteRepository
import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import kotlinx.coroutines.launch

class BarbersViewModel(
    private val barbersRemoteRepository: BarbersRemoteRepository
) : ViewModel() {

    private val _barberUiState = MutableLiveData<BarbersUiState>(BarbersUiState.Loading)
    val barberUiState: LiveData<BarbersUiState>
        get() = _barberUiState

    init {
        fetchBarbersAvailable(
            weekDayRange = "1-1",
            hourRange = "8-21"
        )
    }

    fun fetchBarbersAvailable(weekDayRange: String, hourRange: String) {
        viewModelScope.launch {
            when (val result = barbersRemoteRepository.listBarbers(weekDayRange, hourRange)) {
                is Response.Error -> _barberUiState.postValue(BarbersUiState.Error(result.exception.message.orEmpty()))
                is Response.Success -> _barberUiState.postValue(BarbersUiState.Data(result.data.orEmpty()))
            }
        }
    }

}

sealed class BarbersUiState {
    data class Error(val message: String) : BarbersUiState()
    data class Data(val barbers: List<Barber>) : BarbersUiState()
    object Loading : BarbersUiState()
}