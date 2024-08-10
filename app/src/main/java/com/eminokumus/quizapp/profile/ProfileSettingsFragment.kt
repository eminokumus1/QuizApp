package com.eminokumus.quizapp.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.R
import com.eminokumus.quizapp.databinding.FragmentProfileSettingsBinding
import com.eminokumus.quizapp.databinding.WarningDialogBinding
import com.eminokumus.quizapp.login.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

        setChangeEmailBtnOnClickListener(user)
        setChangeUsernameBtnOnClickListener(user)
        setChangePasswordBtnOnClickListener(user)
        setBackBtnOnClickListener()
        setDeleteAccountBtnOnClickListener()

    }

    private fun setBackBtnOnClickListener() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(ProfileSettingsFragmentDirections.actionProfileSettingsFragmentToProfileFragment())
        }
    }

    private fun setChangePasswordBtnOnClickListener(user: FirebaseUser?) {
        binding.changePasswordBtn.setOnClickListener {
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
    }

    private fun setChangeUsernameBtnOnClickListener(user: FirebaseUser?) {
        binding.changeUsernameBtn.setOnClickListener {
            val newUsername = binding.changeUsernameEditText.text.toString()
            if (newUsername.isNotEmpty()) {
                if (user != null) {
                    dbFirebase.child(user.uid).child("username")
                        .setValue(newUsername)
                }
                Toast.makeText(requireContext(), "Username changed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setChangeEmailBtnOnClickListener(user: FirebaseUser?) {
        binding.changeEmailBtn.setOnClickListener {
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
    }

    private fun setDeleteAccountBtnOnClickListener(){
        binding.deleteAccountBtn.setOnClickListener {
            showWarningDialog()
        }
    }

    private fun showWarningDialog(){
        val warningBinding = WarningDialogBinding.inflate(layoutInflater)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val dialog: AlertDialog = builder.create()
        warningBinding.noButton.setOnClickListener {
            dialog.dismiss()
        }
        warningBinding.yesButton.setOnClickListener {
            val user = auth.currentUser
            user?.delete()?.addOnCompleteListener {
                if (it.isSuccessful){
                    println("User Uid: ${user.uid}")
                    dbFirebase.child(user.uid).removeValue()
                    Toast.makeText(requireContext(), "Account deleted", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }else{
                    Log.e("error", it.exception.toString())
                }
            }
        }
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)

        dialog.setView(warningBinding.root)
        dialog.show()
    }

}