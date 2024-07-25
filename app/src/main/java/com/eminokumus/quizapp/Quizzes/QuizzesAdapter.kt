package com.eminokumus.quizapp.Quizzes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.quizapp.databinding.ItemQuizzesBinding
import com.eminokumus.quizapp.vo.Quiz

class QuizzesAdapter : RecyclerView.Adapter<QuizzesAdapter.MyViewHolder>() {

    var dataList = listOf<Quiz>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onQuizItemClickListener: OnQuizItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQuizzesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, onQuizItemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

     class MyViewHolder(private var binding: ItemQuizzesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz, onQuizItemClickListener: OnQuizItemClickListener?){
            binding.quizNameText.text = quiz.name

            binding.root.setOnClickListener {
                onQuizItemClickListener?.onClick(quiz)
            }
        }

    }



}

interface OnQuizItemClickListener {
    fun onClick(quiz: Quiz)
}