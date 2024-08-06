package com.eminokumus.quizapp.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SolvedQuiz(
    val name: String,
    val solvedQuestionList: List<SolvedQuestion>

) : Parcelable
