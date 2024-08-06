package com.eminokumus.quizapp.Quizzes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.quizapp.MyDatabase
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.databinding.FragmentQuizzesBinding
import com.eminokumus.quizapp.vo.Quiz
import com.eminokumus.quizapp.vo.SolvedQuiz
import javax.inject.Inject


class QuizzesFragment : Fragment() {
    private lateinit var binding: FragmentQuizzesBinding

    private val args: QuizzesFragmentArgs by navArgs()


    private val screenType by lazy {
        args.screenType
    }

    @Inject
    lateinit var viewModel: QuizzesViewModel

    @Inject
    lateinit var myDatabase: MyDatabase

    companion object{
        val quizzesScreen = 0
        val solvedQuizzesScreen = 1
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizzesBinding.inflate(layoutInflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quizzesAdapter = QuizzesAdapter(screenType).also {
            if (screenType == quizzesScreen){
                it.onQuizItemClickListener = object : OnQuizItemClickListener{
                    override fun onClick(quiz: Quiz) {
                        findNavController().navigate(QuizzesFragmentDirections.actionQuizzesFragmentToQuizFragment(quiz))
                    }

                }
            } else if (screenType == solvedQuizzesScreen){
                it.onSolvedQuizItemClickListener = object : OnSolvedQuizItemClickListener{
                    override fun onClick(solvedQuiz: SolvedQuiz) {
                        findNavController().navigate(QuizzesFragmentDirections.actionQuizzesFragmentToSolvedQuizDetailsFragment(solvedQuiz))
                    }
                }
            }
        }

        if (screenType == quizzesScreen){
            viewModel.quizList.observe(viewLifecycleOwner){
                quizzesAdapter.quizList = it
            }
        } else if (screenType == solvedQuizzesScreen){
            myDatabase.solvedQuizList.observe(viewLifecycleOwner){
                quizzesAdapter.solvedQuizList = it
            }
        }



        binding.quizzesRecycler.adapter = quizzesAdapter


    }


}

