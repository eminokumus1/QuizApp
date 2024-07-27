package com.eminokumus.quizapp.di

import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.Quizzes.QuizzesFragment
import com.eminokumus.quizapp.login.LoginActivity
import com.eminokumus.quizapp.profile.ProfileFragment
import com.eminokumus.quizapp.profile.ProfileSettingsFragment
import com.eminokumus.quizapp.quiz.QuizFragment
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
    fun inject(fragment: QuizzesFragment)
    fun inject(fragment: QuizFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: ProfileSettingsFragment)
}