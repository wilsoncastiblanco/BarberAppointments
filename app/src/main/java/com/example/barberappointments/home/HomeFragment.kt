package com.example.barberappointments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barberappointments.R
import com.example.barberappointments.login.AuthUiState
import com.example.barberappointments.login.AuthViewModel
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