package com.eminokumus.quizapp.solvedquizdetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.databinding.FragmentSolvedQuizDetailsBinding
import javax.inject.Inject


class SolvedQuizDetailsFragment : Fragment() {
    private lateinit var binding: FragmentSolvedQuizDetailsBinding

    private val args: SolvedQuizDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: SolvedQuizDetailViewModel

    private val solvedQuizDetailsAdapter = SolvedQuizDetailsAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSolvedQuizDetailsBinding.inflate(layoutInflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.solvedQuestionList.observe(viewLifecycleOwner){
            solvedQuizDetailsAdapter.data = it
        }

        viewModel.updateSolvedQuestionList(args.solvedQuiz.solvedQuestionList)

        binding.solvedQuizRecycler.adapter = solvedQuizDetailsAdapter

    }

}