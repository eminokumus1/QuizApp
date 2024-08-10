package com.eminokumus.quizapp.Quizzes

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.databinding.FragmentQuizzesBinding
import com.eminokumus.quizapp.vo.Quiz
import com.eminokumus.quizapp.vo.SolvedQuiz
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import javax.inject.Inject


class QuizzesFragment : Fragment() {
    private lateinit var binding: FragmentQuizzesBinding

    private val args: QuizzesFragmentArgs by navArgs()


    private val screenType by lazy {
        args.screenType
    }

    @Inject
    lateinit var viewModel: QuizzesViewModel


    private lateinit var auth: FirebaseAuth
    private lateinit var dbFirebase: DatabaseReference
    private lateinit var solvedQuizListRef: DatabaseReference

    companion object {
        val quizzesScreen = 0
        val solvedQuizzesScreen = 1
    }

    private lateinit var quizzesAdapter: QuizzesAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizzesBinding.inflate(layoutInflater, container, false)

        viewModel.quizListStateParcel?.let { stateParcelable ->
            binding.quizzesRecycler.layoutManager?.onRestoreInstanceState(stateParcelable)
            viewModel.quizListStateParcel = null
        }

        auth = Firebase.auth
        val user = auth.currentUser
        if (user != null) {
            dbFirebase = Firebase.database.getReference("user").child(user.uid)
            solvedQuizListRef = dbFirebase.child("solvedQuizList")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        quizzesAdapter = QuizzesAdapter(screenType).also {
            if (screenType == quizzesScreen) {
                it.onQuizItemClickListener = object : OnQuizItemClickListener {
                    override fun onClick(quiz: Quiz) {
                        findNavController().navigate(
                            QuizzesFragmentDirections.actionQuizzesFragmentToQuizFragment(
                                quiz
                            )
                        )
                    }

                }
            } else if (screenType == solvedQuizzesScreen) {
                it.onSolvedQuizItemClickListener = object : OnSolvedQuizItemClickListener {
                    override fun onClick(solvedQuiz: SolvedQuiz) {
                        findNavController().navigate(
                            QuizzesFragmentDirections.actionQuizzesFragmentToSolvedQuizDetailsFragment(
                                solvedQuiz
                            )
                        )
                    }
                }
                getSolvedQuizListData()
            }
        }

        if (screenType == quizzesScreen) {
            viewModel.quizList.observe(viewLifecycleOwner) {
                quizzesAdapter.quizList = it
            }
        } else if (screenType == solvedQuizzesScreen) {
            viewModel.solvedQuizList.observe(viewLifecycleOwner) {
                quizzesAdapter.solvedQuizList = it
            }
        }


        binding.quizzesRecycler.adapter = quizzesAdapter


    }

    override fun onDestroyView() {
        val recyclerState = binding.quizzesRecycler.layoutManager?.onSaveInstanceState()
        recyclerState?.let { viewModel.saveQuizListState(recyclerState) }

        super.onDestroyView()
    }

    private fun getSolvedQuizListData() {
        val mutableSolvedQuizList = mutableListOf<SolvedQuiz>()
        solvedQuizListRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    val solvedQuiz = item.getValue(SolvedQuiz::class.java)
                    if (solvedQuiz != null) {
                        mutableSolvedQuizList.add(solvedQuiz)
                    }
                    viewModel.updateSolvedQuizList(mutableSolvedQuizList.sortedBy { it.name })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("error", error.toString())
            }

        })
    }


}

