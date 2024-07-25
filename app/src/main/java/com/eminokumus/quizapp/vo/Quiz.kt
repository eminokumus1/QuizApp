package com.eminokumus.quizapp.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quiz(
    val name: String,
    val questionList: List<Question>
): Parcelable
