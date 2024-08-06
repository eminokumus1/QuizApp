package com.eminokumus.quizapp.vo

import android.os.Parcelable
import com.eminokumus.quizapp.solvedquizdetails.QuestionStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class SolvedQuestion(
    val question: Question,
    val answer: String,
    val questionStatus: QuestionStatus
) : Parcelable
