package com.eminokumus.quizapp.profile

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val password = MutableLiveData<String>()


    fun changeUserEmail(newEmail: String) {
        _userEmail.value = newEmail
    }

    fun changeUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun changePassword(newPassword: String) {
        password.value = newPassword
    }

    fun isEmailValid(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    fun isPasswordValid(userPassword: String): Boolean{
        return userPassword.length >= 6 && userPassword.isNotEmpty()
    }

}