package com.eminokumus.quizapp.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.Quizzes.QuizzesFragment
import com.eminokumus.quizapp.databinding.FragmentProfileBinding
import com.eminokumus.quizapp.login.LoginActivity
import com.eminokumus.quizapp.vo.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

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
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        auth = Firebase.auth
        dbFirebase = Firebase.database.getReference("user")

        observeViewModel()
        updateFields()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun observeViewModel() {
        observeEmail()
        observeUsername()
    }

    private fun observeUsername() {
        viewModel.username.observe(viewLifecycleOwner){newUsername->
            binding.usernameText.text = newUsername
        }
    }

    private fun observeEmail() {
        viewModel.userEmail.observe(viewLifecycleOwner){newEmail->
            binding.userEmailText.text = newEmail
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.run {
            settingsButton.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToProfileSettingsFragment())
            }
            solvedQuizzesBtn.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToQuizzesFragment(QuizzesFragment.solvedQuizzesScreen))
            }
            signOutButton.setOnClickListener {
                auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
        }
    }

    private fun updateFields(){
        val userId = auth.currentUser?.uid
        dbFirebase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = userId?.let { snapshot.child(it).getValue(User::class.java) }
                viewModel.changeUsername(user?.username ?: "")
                viewModel.changeUserEmail(user?.email ?: "")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}