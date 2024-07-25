package com.eminokumus.quizapp.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val questionLine: String,
    val answers: List<String>,
    val correctAnswer: String
):Parcelable
