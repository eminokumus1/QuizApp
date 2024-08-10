package com.eminokumus.quizapp.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val questionLine: String? = null,
    val answers: List<String>? = null,
    val correctAnswer: String? = null
):Parcelable
