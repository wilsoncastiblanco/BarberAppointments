package com.example.barberappointments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servall.data.datasource.AppointmentsDataSourceRepository
import com.servall.data.datasource.BarbersDataSourceRepository
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import kotlinx.coroutines.launch

class AppointmentsViewModel(
    private val barbersDataSourceRepository: BarbersDataSourceRepository,
    private val appointmentsDataSourceRepository: AppointmentsDataSourceRepository
) : ViewModel() {
    private val _appointmentsUiState = MutableLiveData<AppointmentsUiState>()
    val appointmentsUiState: LiveData<AppointmentsUiState>
        get() = _appointmentsUiState

    fun observeAppointments(user: User) {
        viewModelScope.launch {
            appointmentsDataSourceRepository.listStreamCustomerAppointments(user.id)
                .collect { result ->
                    handleAppointmentsResult(result)
                }
        }
    }

    fun fetchAppointments(user: User) {
        viewModelScope.launch {
            val result = appointmentsDataSourceRepository.fetchAppointments(user.id)
            handleAppointmentsResult(result)
        }
    }

    private fun handleAppointmentsResult(result: Response<List<Appointment>>) {
        when (result) {
            is Response.Error -> _appointmentsUiState.postValue(
                AppointmentsUiState.Error(
                    result.exception.message.orEmpty()
                )
            )

            is Response.Success -> _appointmentsUiState.postValue(
                AppointmentsUiState.Appointments(
                    result.data
                )
            )
        }
    }

    fun fetchAvailableBarbers(weekDayRange: String, hourRange: String) {
        viewModelScope.launch {
            when (val result = barbersDataSourceRepository.listBarbers(weekDayRange, hourRange)) {
                is Response.Error -> _appointmentsUiState.postValue(AppointmentsUiState.Error(result.exception.message.orEmpty()))
                is Response.Success -> _appointmentsUiState.postValue(
                    AppointmentsUiState.AvailableBarbers(
                        result.data
                    )
                )
            }
        }
    }

    fun setAppointment(barber: Barber, user: User) {
        viewModelScope.launch {
            val timeStamp: Long = System.currentTimeMillis()
            when (val result = appointmentsDataSourceRepository.createAppointment(
                dateTime = timeStamp,
                barberId = barber.id,
                customerId = user.id
            )) {
                is Response.Error -> _appointmentsUiState.postValue(AppointmentsUiState.Error(result.exception.message.orEmpty()))
                is Response.Success -> _appointmentsUiState.postValue(
                    AppointmentsUiState.Created(
                        result.data
                    )
                )
            }
        }
    }

    fun cancelAppointment(appointment: Appointment) {
        viewModelScope.launch {
            //val result = appointmentsDataSourceRepository.cancelAppointment(appointment)
        }
    }

}

sealed class AppointmentsUiState {
    data class AvailableBarbers(val barbers: List<Barber>) : AppointmentsUiState()
    data class Created(val appointmentId: String) : AppointmentsUiState()
    data class Appointments(val appointments: List<Appointment>) : AppointmentsUiState()
    data class Error(val message: String) : AppointmentsUiState()
}
