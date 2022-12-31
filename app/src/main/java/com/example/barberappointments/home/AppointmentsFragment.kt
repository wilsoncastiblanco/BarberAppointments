package com.example.barberappointments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barberappointments.R
import com.example.barberappointments.availablebarbers.AvailableBarbersBottomSheet
import com.example.barberappointments.login.AuthUiState
import com.example.barberappointments.login.AuthViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.servall.domain.entities.Appointment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AppointmentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppointmentsFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModel()
    private val appointmentsViewModel: AppointmentsViewModel by viewModel()
    private lateinit var appointmentsAdapter: AppointmentsAdapter
    private lateinit var availableBarbersBottomSheet: AvailableBarbersBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        observeAppointments()
        observeAuthentication()
    }

    private fun observeAppointments() {
        appointmentsViewModel.appointmentsUiState.observe(viewLifecycleOwner) { appointmentState ->
            when (appointmentState) {
                is AppointmentsUiState.Error -> Toast.makeText(
                    requireContext(),
                    "Error! ${appointmentState.message}",
                    Toast.LENGTH_LONG
                ).show()

                is AppointmentsUiState.Appointments -> {
                    appointmentsAdapter.submitList(appointmentState.appointments)
                }

                else -> {}
            }
        }
    }

    private fun initViews(view: View) {

        val appointmentsRecyclerView =
            view.findViewById<RecyclerView>(R.id.recyclerViewAppointments)
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        appointmentsAdapter = AppointmentsAdapter()
        appointmentsRecyclerView.adapter = appointmentsAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolderOrigin: RecyclerView.ViewHolder,
                viewHolderDestination: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, dir: Int) {
                val appointment: Appointment = appointmentsAdapter.currentList[viewHolder.adapterPosition]
                appointmentsViewModel.cancelAppointment(appointment)
            }
        }).attachToRecyclerView(appointmentsRecyclerView)

        view.findViewById<FloatingActionButton>(R.id.fabCreateAppointment).setOnClickListener {
            availableBarbersBottomSheet = AvailableBarbersBottomSheet()
            availableBarbersBottomSheet.show(childFragmentManager, AvailableBarbersBottomSheet.TAG)
        }

        childFragmentManager.setFragmentResultListener(
            "appointmentResult",
            viewLifecycleOwner
        ) { _, _ ->
            availableBarbersBottomSheet.dismiss()
            Snackbar.make(view, "Appointment created!", Snackbar.LENGTH_LONG)
                .setAction("Revert", {})
                .show()
        }
    }


    private fun observeAuthentication() {
        val navController = findNavController()
        authViewModel.authState.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                is AuthUiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error ${authState.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is AuthUiState.UnAuthenticated -> {
                    navController.navigate(R.id.loginFragment)
                }

                is AuthUiState.Authenticated -> {
                    appointmentsViewModel.fetchAppointments(authState.user!!)
                    appointmentsViewModel.observeAppointments(authState.user)
                }

                else -> {}
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AppointmentsFragment()
    }
}