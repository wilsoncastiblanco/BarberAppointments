package com.example.barberappointments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servall.data.remote.AppointmentRemoteRepository
import com.servall.data.remote.BarbersRemoteRepository
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import kotlinx.coroutines.launch

class BarbersViewModel(
    private val barbersRemoteRepository: BarbersRemoteRepository,
    private val appointmentRemoteRepository: AppointmentRemoteRepository
) : ViewModel() {

    private val _barberUiState = MutableLiveData<BarbersUiState>(BarbersUiState.Loading)
    val barberUiState: LiveData<BarbersUiState>
        get() = _barberUiState

    private val _appointmentsUiState = MutableLiveData<AppointmentsUiState>()
    val appointmentsUiState: LiveData<AppointmentsUiState>
        get() = _appointmentsUiState

    init {
        fetchBarbersAvailable(
            weekDayRange = "1-1",
            hourRange = "9-21"
        )
    }

    fun fetchBarbersAvailable(weekDayRange: String, hourRange: String) {
        viewModelScope.launch {
            when (val result = barbersRemoteRepository.listBarbers(weekDayRange, hourRange)) {
                is Response.Error -> _barberUiState.postValue(BarbersUiState.Error(result.exception.message.orEmpty()))
                is Response.Success -> _barberUiState.postValue(BarbersUiState.Data(result.data))
            }
        }
    }

    fun setAppointment(barber: Barber, user: User) {
        viewModelScope.launch {
            val timeStamp: Long = System.currentTimeMillis()
            when (val result = appointmentRemoteRepository.createAppointment(
                dateTime = timeStamp,
                barberId = barber.id,
                customerId = user.id
            )) {
                is Response.Error -> _appointmentsUiState.postValue(AppointmentsUiState.Error(result.exception.message.orEmpty()))
                is Response.Success -> _appointmentsUiState.postValue(AppointmentsUiState.Created(result.data))
            }
        }
    }

}

sealed class AppointmentsUiState {
    data class Created(val appointment: Appointment) : AppointmentsUiState()
    data class Error(val message: String) : AppointmentsUiState()
}


sealed class BarbersUiState {
    data class Error(val message: String) : BarbersUiState()
    data class Data(val barbers: List<Barber>) : BarbersUiState()
    object Loading : BarbersUiState()
}