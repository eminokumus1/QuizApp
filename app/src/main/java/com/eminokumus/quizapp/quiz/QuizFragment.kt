package com.eminokumus.quizapp.quiz

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.R
import com.eminokumus.quizapp.databinding.FragmentQuizBinding
import javax.inject.Inject


class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding

    @Inject
    lateinit var viewModel: QuizViewModel

    private val args: QuizFragmentArgs by navArgs()

    private val handler = Handler(Looper.getMainLooper())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater, container, false)

        binding.viewModel = viewModel

//        binding.question = args.quiz.questionList[0]

        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.questionList = args.quiz.questionList

        viewModel.updateQuestion()

        binding.nextButton.setOnClickListener {
            isListEndedObserver(it)
        }


    }

    private fun isListEndedObserver(it: View) {
        viewModel.isListEnded.observe(viewLifecycleOwner) { isEnded ->
            if (isEnded && findNavController().currentDestination?.id == R.id.quizFragment) {
                findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToScoreFragment(
                        args.quiz,
                        viewModel.score.value ?: 0
                    )
                )
            }
        }
        checkAnswer()
        it.isClickable = false
        handler.postDelayed({
            viewModel.updateQuestion()
            it.isClickable = true
        }, 500)
    }

    private fun checkAnswer() {
        val checkedId = binding.answersRadioGroup.checkedRadioButtonId
        when (checkedId) {
            binding.firstAnswer.id -> viewModel.updateAnswer(binding.firstAnswer.text.toString())
            binding.secondAnswer.id -> viewModel.updateAnswer(binding.secondAnswer.text.toString())
            binding.thirdAnswer.id -> viewModel.updateAnswer(binding.thirdAnswer.text.toString())
            binding.fourthAnswer.id -> viewModel.updateAnswer(binding.fourthAnswer.text.toString())
        }
        if (viewModel.isAnswerCorrect()) {

            Toast.makeText(context, "Correct", Toast.LENGTH_SHORT).show()
            viewModel.updateScore()
        } else
            Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show()
    }


}