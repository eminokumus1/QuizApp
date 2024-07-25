package com.eminokumus.quizapp.di

import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.Quizzes.QuizzesFragment
import com.eminokumus.quizapp.login.LoginActivity
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
    fun inject(fragment: QuizzesFragment)
}