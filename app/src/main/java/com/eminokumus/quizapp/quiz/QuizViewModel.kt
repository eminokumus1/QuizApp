package com.eminokumus.quizapp.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.quizapp.solvedquizdetails.QuestionStatus
import com.eminokumus.quizapp.vo.Question
import com.eminokumus.quizapp.vo.SolvedQuestion
import javax.inject.Inject


class QuizViewModel @Inject constructor() : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question> get() = _question

    private val _isListEnded = MutableLiveData<Boolean>()
    val isListEnded: LiveData<Boolean> get() = _isListEnded


    private var answer = ""

    private var solvedQuestionList = mutableListOf<SolvedQuestion>()

    private var questionStatus: QuestionStatus = QuestionStatus.WRONG

    private var questionIndex = 0

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

    fun updateQuestionStatus(newStatus: QuestionStatus){
        questionStatus = newStatus
    }

    fun isAnswerCorrect(): Boolean{
        return answer == question.value?.correctAnswer
    }

    fun findCorrectAnswerIndex(): Int{
        when(question.value?.correctAnswer){
            question.value?.answers?.get(0) -> return 0
            question.value?.answers?.get(1) -> return 1
            question.value?.answers?.get(2) -> return 2
            question.value?.answers?.get(3) -> return 3
        }
        return -1
    }

    fun addSolvedQuestionToList(){
        solvedQuestionList.add(SolvedQuestion(question.value!!, answer, questionStatus))
    }

    fun getSolvedQuestionList(): MutableList<SolvedQuestion>{
        return solvedQuestionList
    }



}

