package com.eminokumus.quizapp.vo

data class Question(
    val questionLine: String,
    val answers: List<String>,
    val correctAnswer: String
)
