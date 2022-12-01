package com.example.barberappointments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barberappointments.R
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModel()

    private lateinit var editTextUserName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextUserName = view.findViewById(R.id.editTextUserName)
        editTextUserName.setText( "angel.vazquez")
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            authViewModel.login(
                userName = editTextUserName.text.toString(),
                password = editTextPassword.text.toString()
            )
        }
        observeAuthentication()
    }

    private fun observeAuthentication() {
        authViewModel.authState.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                is AuthUiState.Authenticated -> {
                    findNavController().popBackStack()
                }
                is AuthUiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error! ${authState.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                AuthUiState.UnAuthenticated -> {}
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment()
    }
}