package com.eminokumus.quizapp.Quizzes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.R
import com.eminokumus.quizapp.databinding.FragmentQuizzesBinding
import com.eminokumus.quizapp.vo.Quiz
import javax.inject.Inject


class QuizzesFragment : Fragment() {
    private lateinit var binding: FragmentQuizzesBinding

    @Inject
    lateinit var viewModel: QuizzesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    private val quizzesAdapter = QuizzesAdapter().also {
        it.onQuizItemClickListener = object : OnQuizItemClickListener{
            override fun onClick(quiz: Quiz) {
                findNavController().navigate(QuizzesFragmentDirections.actionQuizzesFragmentToQuizFragment(quiz))
            }

        }
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

        viewModel.quizList.observe(viewLifecycleOwner){
            quizzesAdapter.dataList = it
            println(it.get(0))
        }

        binding.quizzesRecycler.adapter = quizzesAdapter



    }


}