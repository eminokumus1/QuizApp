package com.eminokumus.quizapp.Quizzes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.quizapp.databinding.ItemQuizzesBinding
import com.eminokumus.quizapp.vo.Quiz
import com.eminokumus.quizapp.vo.SolvedQuiz

class QuizzesAdapter(private val screenType: Int) : RecyclerView.Adapter<QuizzesAdapter.MyViewHolder>() {

    var quizList = listOf<Quiz>()
        set(value) {
            field = value
            notifyDataSetChanged()

        }

    var solvedQuizList = listOf<SolvedQuiz>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var onQuizItemClickListener: OnQuizItemClickListener? = null

    var onSolvedQuizItemClickListener: OnSolvedQuizItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQuizzesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(screenType == QuizzesFragment.quizzesScreen){
            val item = quizList[position]
            holder.bindQuiz(item, onQuizItemClickListener)
        }else if (screenType == QuizzesFragment.solvedQuizzesScreen){
            val item = solvedQuizList[position]
            holder.bindSolvedQuiz(item, onSolvedQuizItemClickListener)
        }
    }

    override fun getItemCount(): Int {
        return if (screenType == QuizzesFragment.quizzesScreen){
            quizList.size
        } else{
            solvedQuizList.size
        }
    }

     class MyViewHolder(private var binding: ItemQuizzesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindQuiz(quiz: Quiz, onQuizItemClickListener: OnQuizItemClickListener?){
            binding.quizNameText.text = quiz.name


            binding.root.setOnClickListener {
                onQuizItemClickListener?.onClick(quiz)
            }
        }
         fun bindSolvedQuiz(solvedQuiz: SolvedQuiz, onSolvedQuizItemClickListener: OnSolvedQuizItemClickListener?){
             binding.quizNameText.text = solvedQuiz.name

             binding.root.setOnClickListener{
                 onSolvedQuizItemClickListener?.onClick(solvedQuiz)
             }
         }

    }



}

interface OnQuizItemClickListener {
    fun onClick(quiz: Quiz)
}

interface OnSolvedQuizItemClickListener{
    fun onClick(solvedQuiz: SolvedQuiz)
}