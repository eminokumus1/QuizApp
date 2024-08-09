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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private lateinit var auth: FirebaseAuth

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

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
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


}