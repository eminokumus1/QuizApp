package com.eminokumus.quizapp

import android.app.Application
import com.eminokumus.quizapp.di.DaggerAppComponent

class MyApplication: Application() {
    val appComponent = DaggerAppComponent.create()
}