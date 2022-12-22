package com.example.barberappointments.di

import com.example.barberappointments.home.BarbersViewModel
import com.example.barberappointments.login.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AuthViewModel(loginRepository = get()) }
    viewModel { BarbersViewModel(barbersRemoteRepository = get()) }
}