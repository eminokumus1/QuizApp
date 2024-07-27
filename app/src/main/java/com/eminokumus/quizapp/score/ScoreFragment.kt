package com.eminokumus.quizapp.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.quizapp.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding

    private val args: ScoreFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scoreText.text = "Score: ${args.score}"

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.retryButton.setOnClickListener {
            findNavController().navigate(
                ScoreFragmentDirections.actionScoreFragmentToQuizFragment(
                    args.quiz
                )
            )
        }

        binding.backHomeButton.setOnClickListener {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToQuizzesFragment())
        }
    }

}