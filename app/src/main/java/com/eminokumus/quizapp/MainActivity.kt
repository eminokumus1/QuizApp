package com.eminokumus.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.quizapp.di.AppComponent

class MainActivity : AppCompatActivity() {

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}