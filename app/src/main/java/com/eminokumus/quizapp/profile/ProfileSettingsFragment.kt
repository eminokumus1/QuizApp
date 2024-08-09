package com.eminokumus.quizapp.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.databinding.FragmentProfileSettingsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import javax.inject.Inject


class ProfileSettingsFragment : Fragment() {
    private lateinit var binding: FragmentProfileSettingsBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var dbFirebase: DatabaseReference

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

        auth = Firebase.auth
        dbFirebase = Firebase.database.getReference("user")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        val user = auth.currentUser
        binding.run {
            changeEmailBtn.setOnClickListener {
                val newEmail = binding.changeEmailEditText.text.toString()
                if (viewModel.isEmailValid(newEmail)) {
                    if (user != null) {
                        user.verifyBeforeUpdateEmail(newEmail).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    "Email changed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Log.e("error", it.exception.toString())
                            }
                        }
                        dbFirebase.child(user.uid).child("email").setValue(newEmail)
                    }

                } else {
                    Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                }
            }
            changeUsernameBtn.setOnClickListener {
                val newUsername = binding.changeUsernameEditText.text.toString()
                if (newUsername.isNotEmpty()) {
                    if (user != null) {
                        dbFirebase.child(user.uid).child("username")
                            .setValue(newUsername)
                    }
                    Toast.makeText(requireContext(), "Username changed", Toast.LENGTH_SHORT).show()
                }
            }
            changePasswordBtn.setOnClickListener {
                val newPassword = binding.changePasswordEditText.text.toString()
                if (viewModel.isPasswordValid(newPassword)) {
                    val credential =
                        user?.email?.let { it1 -> EmailAuthProvider.getCredential(it1, newPassword) }
                    if (credential != null) {
                        user.reauthenticate(credential)
                    }
                    user?.updatePassword(newPassword)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Password changed",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.e("error", it.exception.toString())
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Minimum password length is 6",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            backButton.setOnClickListener {
                findNavController().navigate(ProfileSettingsFragmentDirections.actionProfileSettingsFragmentToProfileFragment())
            }
        }


    }
}