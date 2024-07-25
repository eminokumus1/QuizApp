package com.eminokumus.quizapp.Quizzes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.quizapp.databinding.ItemQuizzesBinding
import com.eminokumus.quizapp.vo.Quiz

class QuizzesAdapter : RecyclerView.Adapter<QuizzesAdapter.MyViewHolder>() {

    var dataList = listOf<Quiz>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQuizzesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(var binding: ItemQuizzesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz){
            binding.quizNameText.text = quiz.name
        }

    }


}