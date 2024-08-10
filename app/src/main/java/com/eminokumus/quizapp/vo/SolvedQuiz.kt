package com.eminokumus.quizapp.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SolvedQuiz(
    val name: String? = null,
    val solvedQuestionList: List<SolvedQuestion>? = null
) : Parcelable
