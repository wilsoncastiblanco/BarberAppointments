package com.example.barberappointments.home

import android.app.AlertDialog
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
import com.servall.domain.entities.Barber
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModel()
    private val barbersViewModel: BarbersViewModel by viewModel()

    private lateinit var barbersAdapter: BarbersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val barbersRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewBarbers)
        barbersRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        barbersAdapter = BarbersAdapter { barber ->
            showAlertDialog(barber)
        }
        barbersRecyclerView.adapter = barbersAdapter
        observeAuthentication()
        observeBarbersAvailable()
    }

    private fun showAlertDialog(barber: Barber) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm barber appointment")
            .setMessage("Do you want to set an appointment with ${barber.fullName}?")
            .setPositiveButton(
                "Yes"
            ) { dialog, which ->
                barbersViewModel.setAppointment(barber, (authViewModel.authState.value as AuthUiState.Authenticated).user!!)
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun observeBarbersAvailable() {
        barbersViewModel.barberUiState.observe(viewLifecycleOwner) { barbersState ->
            when (barbersState) {
                is BarbersUiState.Data -> barbersAdapter.setBarbers(barbersState.barbers)
                is BarbersUiState.Error -> Toast.makeText(
                    requireContext(),
                    "Error! ${barbersState.message}",
                    Toast.LENGTH_LONG
                ).show()
                BarbersUiState.Loading -> {}
            }
        }
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