package com.eminokumus.quizapp.vo

data class User(
    val userId: String? = null,
    val email: String? = null,
    val username: String? = null,
    val solvedQuizList: List<SolvedQuiz>? = null
)
