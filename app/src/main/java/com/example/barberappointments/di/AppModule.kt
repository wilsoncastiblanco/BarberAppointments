package com.example.barberappointments.di

import com.example.barberappointments.home.AppointmentsViewModel
import com.example.barberappointments.login.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AuthViewModel(loginRepository = get()) }
    viewModel { AppointmentsViewModel(barbersDataSourceRepository = get(), appointmentsDataSourceRepository = get()) }
}