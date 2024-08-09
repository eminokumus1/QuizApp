package com.eminokumus.quizapp.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(): ViewModel() {

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    fun checkEmailFormat(email: String){
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

}