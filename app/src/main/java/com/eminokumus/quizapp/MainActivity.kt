package com.eminokumus.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.eminokumus.quizapp.databinding.ActivityMainBinding
import com.eminokumus.quizapp.di.AppComponent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.myNavHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController


        binding.bottombar.setupWithNavController(navController)

    }



}