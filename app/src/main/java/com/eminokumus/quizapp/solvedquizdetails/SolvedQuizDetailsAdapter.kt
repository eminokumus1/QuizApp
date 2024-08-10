package com.eminokumus.quizapp.solvedquizdetails

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.quizapp.databinding.ItemSolvedQuizDetailsBinding
import com.eminokumus.quizapp.vo.SolvedQuestion

class SolvedQuizDetailsAdapter: RecyclerView.Adapter<SolvedQuizDetailsAdapter.SolvedQuizViewHolder>() {

    var data = listOf<SolvedQuestion>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvedQuizViewHolder {
        val binding = ItemSolvedQuizDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SolvedQuizViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SolvedQuizViewHolder, position: Int) {
        val solvedQuestion = data[position]
        holder.bind(solvedQuestion)
    }

    inner class SolvedQuizViewHolder(private val binding: ItemSolvedQuizDetailsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(solvedQuestion: SolvedQuestion){
            binding.run {
                questionText.text = solvedQuestion.question?.questionLine
                answerText.text= "Your answer: ${ solvedQuestion?.answer }"
                correctAnswerText.text= "Correct answer: ${ solvedQuestion.question?.correctAnswer }"
                questionStatusText.text= solvedQuestion.questionStatus.toString()
            }
            if (binding.questionStatusText.text == QuestionStatus.CORRECT.toString()){
                binding.questionStatusText.setTextColor(Color.GREEN)
            }else if (binding.questionStatusText.text == QuestionStatus.WRONG.toString()){
                binding.questionStatusText.setTextColor(Color.RED)
            }
        }
    }
}