package com.eminokumus.quizapp.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.quizapp.vo.Question
import javax.inject.Inject


class QuizViewModel @Inject constructor() : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question> get() = _question

    private val _isListEnded = MutableLiveData<Boolean>()
    val isListEnded: LiveData<Boolean> get() = _isListEnded


    private var answer = ""

    var questionIndex = 0

    var questionList = listOf<Question>()

    init {
        _score.value = 0
        _isListEnded.value = false
    }

    fun updateScore() {
        _score.value = _score.value?.plus(1)
    }

    fun updateQuestion() {
        if (questionIndex < questionList.size ) {
            _question.value = questionList[questionIndex]
            questionIndex++
        } else{
            _isListEnded.value = true
        }
    }

    fun updateAnswer(givenAnswer: String){
        answer = givenAnswer
    }

    fun isAnswerCorrect(): Boolean{
        return answer == question.value?.correctAnswer
    }



}

