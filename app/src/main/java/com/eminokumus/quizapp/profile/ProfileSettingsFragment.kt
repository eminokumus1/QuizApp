package com.eminokumus.quizapp.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.databinding.FragmentProfileSettingsBinding
import javax.inject.Inject


class ProfileSettingsFragment : Fragment() {
    private lateinit var binding: FragmentProfileSettingsBinding

    @Inject
    lateinit var viewModel: ProfileViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileSettingsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.run {
            changeEmailBtn.setOnClickListener {
                val newEmail = binding.changeEmailEditText.text.toString()
                if (viewModel.isEmailValid(newEmail)) {
                    viewModel.changeUserEmail(newEmail)
                    Toast.makeText(requireContext(), "Email changed", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                }
            }
            changeUsernameBtn.setOnClickListener {
                val newUsername = binding.changeUsernameEditText.text.toString()
                if (newUsername.isNotEmpty()) {
                    viewModel.changeUsername(newUsername)
                    Toast.makeText(requireContext(), "Username changed", Toast.LENGTH_SHORT).show()
                }
            }
            changePasswordBtn.setOnClickListener {
                val newPassword = binding.changePasswordEditText.text.toString()
                if (viewModel.isPasswordValid(newPassword)) {
                    viewModel.changePassword(newPassword)
                    Toast.makeText(requireContext(), "Password changed", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(requireContext(), "Minimum password length is 6", Toast.LENGTH_SHORT).show()
                }
            }
            backButton.setOnClickListener {
                findNavController().navigate(ProfileSettingsFragmentDirections.actionProfileSettingsFragmentToProfileFragment())
            }
        }


    }
}