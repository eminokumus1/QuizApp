package com.eminokumus.quizapp.quiz

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.R
import com.eminokumus.quizapp.databinding.FragmentQuizBinding
import com.eminokumus.quizapp.solvedquizdetails.QuestionStatus
import com.eminokumus.quizapp.vo.SolvedQuiz
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import javax.inject.Inject


class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding

    @Inject
    lateinit var viewModel: QuizViewModel


    private val args: QuizFragmentArgs by navArgs()

    private val handler = Handler(Looper.getMainLooper())

    var checkedButton: View? = null

    private lateinit var auth: FirebaseAuth

    private lateinit var dbFirebase: DatabaseReference
    private lateinit var solvedQuizListRef: DatabaseReference


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
        binding.lifecycleOwner = this

        auth = Firebase.auth
        dbFirebase = Firebase.database.getReference("user")



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
                val solvedQuiz = SolvedQuiz(args.quiz.name, viewModel.getSolvedQuestionList())
                val user = auth.currentUser
                if (user != null) {
                    solvedQuizListRef = dbFirebase.child(user.uid).child("solvedQuizList")
                    viewModel.addSolvedQuizToFirebase(
                        solvedQuizListRef, solvedQuiz
                    )
                }

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
        binding.answersRadioGroup.clearCheck()

        handler.postDelayed({
            viewModel.updateQuestion()
            it.isClickable = true
        }, 500)
    }

    private fun checkAnswer() {
        val checkedId = binding.answersRadioGroup.checkedRadioButtonId
        when (checkedId) {
            binding.firstAnswer.id -> {
                viewModel.updateAnswer(binding.firstAnswer.text.toString())
                checkedButton = binding.firstAnswer
            }

            binding.secondAnswer.id -> {
                viewModel.updateAnswer(binding.secondAnswer.text.toString())
                checkedButton = binding.secondAnswer
            }

            binding.thirdAnswer.id -> {
                viewModel.updateAnswer(binding.thirdAnswer.text.toString())
                checkedButton = binding.thirdAnswer
            }

            binding.fourthAnswer.id -> {
                viewModel.updateAnswer(binding.fourthAnswer.text.toString())
                checkedButton = binding.fourthAnswer
            }
        }
        if (viewModel.isAnswerCorrect()) {
            viewModel.updateQuestionStatus(QuestionStatus.CORRECT)
            viewModel.addSolvedQuestionToList()
            changeTrueButtonColors(checkedButton)
            viewModel.updateScore()
        } else {
            viewModel.updateQuestionStatus(QuestionStatus.WRONG)
            viewModel.addSolvedQuestionToList()
            changeFalseButtonColors(checkedButton)
        }
        checkedButton = null

    }

    private fun changeTrueButtonColors(checkedButton: View?) {
        if (checkedButton != null) {
            makeViewGreen(checkedButton)
            handler.postDelayed({
                makeViewSoftBlue(checkedButton)

            }, 500)
        }


    }

    private fun changeFalseButtonColors(checkedButton: View?) {
        if (checkedButton != null) {
            makeViewRed(checkedButton)
        }
        var correctAnswer = binding.firstAnswer
        when (viewModel.findCorrectAnswerIndex()) {
            0 -> {
                correctAnswer = binding.firstAnswer
                makeViewGreen(correctAnswer)
            }

            1 -> {
                correctAnswer = binding.secondAnswer
                makeViewGreen(correctAnswer)
            }

            2 -> {
                correctAnswer = binding.thirdAnswer
                makeViewGreen(correctAnswer)

            }

            3 -> {
                correctAnswer = binding.fourthAnswer
                makeViewGreen(correctAnswer)
            }
        }
        handler.postDelayed({
            if (checkedButton != null) {
                makeViewSoftBlue(checkedButton)
            }
            makeViewSoftBlue(correctAnswer)
        }, 500)


    }

    private fun makeViewGreen(view: View) {
        view.setBackgroundResource(R.drawable.bg_rounded_green)
    }

    private fun makeViewRed(view: View) {
        view.setBackgroundResource(R.drawable.bg_rounded_red)
    }

    private fun makeViewSoftBlue(view: View) {
        view.setBackgroundResource(R.drawable.bg_rounded_soft)
    }


}