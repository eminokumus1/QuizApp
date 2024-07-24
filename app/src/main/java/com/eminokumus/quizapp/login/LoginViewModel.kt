package com.eminokumus.quizapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoginViewModel@Inject constructor(): ViewModel() {
    private val userEmail = "eminokumus1@gmail.com"
    private val userPassword = "123456"

    private val _isUserValid = MutableLiveData<Boolean>()
    val isUserValid: LiveData<Boolean> get() = _isUserValid

    fun checkUser(email: String, password: String){
        _isUserValid.value = email == userEmail && password == userPassword
    }
}