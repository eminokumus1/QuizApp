package com.eminokumus.quizapp.solvedquizdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.quizapp.vo.SolvedQuestion
import javax.inject.Inject

class SolvedQuizDetailViewModel @Inject constructor() : ViewModel() {

    private val _solvedQuestionList = MutableLiveData<List<SolvedQuestion>>()
    val solvedQuestionList: LiveData<List<SolvedQuestion>> get() = _solvedQuestionList

    fun updateSolvedQuestionList(list: List<SolvedQuestion>){
        _solvedQuestionList.value = list
    }

}