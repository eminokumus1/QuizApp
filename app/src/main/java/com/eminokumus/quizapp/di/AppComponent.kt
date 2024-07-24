package com.eminokumus.quizapp.di

import com.eminokumus.quizapp.login.LoginActivity
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: LoginActivity)
}