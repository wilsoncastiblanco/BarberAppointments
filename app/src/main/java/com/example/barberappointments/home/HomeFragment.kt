package com.example.barberappointments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barberappointments.R
import com.example.barberappointments.login.AuthUiState
import com.example.barberappointments.login.AuthViewModel
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.User
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appointmentsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        val appointmentsAdapter = AppointmentsAdapter(requireContext())
        val appointments = (1..100).map {
            Appointment(
                "$it",
                dateTime = it.toLong(),
                customer = User(
                    "$it",
                    userName = "User $it",
                    fullName = "Name $it",
                    role = "Role $it",
                    password = null
                ),
                barber = User(
                    "$it",
                    userName = "User $it",
                    fullName = "Name $it",
                    role = "Role $it",
                    password = null
                )
            )
        }
        appointmentsAdapter.setAppointments(appointments)
        appointmentsRecyclerView.adapter = appointmentsAdapter
        observeAuthentication()
    }

    private fun observeAuthentication() {
        val navController = findNavController()
        authViewModel.authState.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                is AuthUiState.Authenticated -> {
                    Toast.makeText(requireContext(), "Welcome!", Toast.LENGTH_LONG).show()
                }
                is AuthUiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error ${authState.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                AuthUiState.UnAuthenticated -> {
                    navController.navigate(R.id.loginFragment)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}