package com.example.barberappointments.availablebarbers

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barberappointments.R
import com.example.barberappointments.home.AppointmentsUiState
import com.example.barberappointments.home.AppointmentsViewModel
import com.example.barberappointments.login.AuthUiState
import com.example.barberappointments.login.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.servall.domain.entities.Barber
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvailableBarbersBottomSheet : BottomSheetDialogFragment() {

    private lateinit var availableBarbersAdapter: AvailableBarbersAdapter
    private val appointmentsViewModel: AppointmentsViewModel by viewModel()
    private val authViewModel: AuthViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appointmentsViewModel.fetchAvailableBarbers(
            weekDayRange = "1-1",
            hourRange = "9-21"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottomsheet_available_barbers, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barbersRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewBarbers)
        barbersRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        availableBarbersAdapter = AvailableBarbersAdapter { barber ->
            showAlertDialog(barber)
        }
        barbersRecyclerView.adapter = availableBarbersAdapter
        observeAppointments()
    }

    private fun observeAppointments() {
        appointmentsViewModel.appointmentsUiState.observe(viewLifecycleOwner) { appointmentState ->
            when (appointmentState) {
                is AppointmentsUiState.AvailableBarbers -> availableBarbersAdapter.setBarbers(appointmentState.barbers)
                is AppointmentsUiState.Error -> Toast.makeText(
                    requireContext(),
                    "Error! ${appointmentState.message}",
                    Toast.LENGTH_LONG
                ).show()
                is AppointmentsUiState.Created -> {
                    parentFragmentManager.setFragmentResult(
                        "appointmentResult",
                        bundleOf("appointmentCreated" to true)
                    )
                }// Serializable -> Reflection
                else -> {}
            }
        }
    }

    private fun showAlertDialog(barber: Barber) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm barber appointment")
            .setMessage("Do you want to set an appointment with ${barber.fullName}?")
            .setPositiveButton(
                "Yes"
            ) { dialog, which ->
                appointmentsViewModel.setAppointment(barber, (authViewModel.authState.value as AuthUiState.Authenticated).user!!)
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }


    companion object {
        const val TAG = "BarbersAvailableBottomSheet"
    }
}