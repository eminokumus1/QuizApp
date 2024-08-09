package com.eminokumus.quizapp.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.MyApplication
import com.eminokumus.quizapp.databinding.ActivityLoginBinding
import com.eminokumus.quizapp.signup.SignupActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        observeViewModel()
        checkFields()
        checkLoggedInUser()

        setOnClickListeners()

    }

    private fun observeViewModel() {
        observeEmail()
        observePassword()
    }

    private fun observePassword() {
        viewModel.isPasswordValid.observe(this) { isValid ->
            if (isValid) {
                binding.passwordField.error = null
            } else {
                binding.passwordField.error = "Minimum 6 characters"
            }
        }
    }

    private fun observeEmail() {
        viewModel.isEmailValid.observe(this) { isValid ->
            if (isValid) {
                binding.emailField.error = null
            } else {
                binding.emailField.error = "Wrong email format"
            }
        }
    }

    private fun setOnClickListeners() {
        setSignupBtnOnClickListener()
        setRootOnClickListener()
        setLoginBtnOnClickListener()
        setForgetPasswordBtnOnClickListener()

    }

    private fun setLoginBtnOnClickListener() {
        binding.loginButton.setOnClickListener {
            if (isLoginAllowed()) {
                auth.signInWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        handler.postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)

                    }else{
                        Log.e("error", it.exception.toString())
                    }

                }
            }else{
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setForgetPasswordBtnOnClickListener(){
        binding.forgetPasswordButton.setOnClickListener{
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRootOnClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun setSignupBtnOnClickListener() {
        binding.signupChoiceButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun checkFields() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
        binding.passwordEditText.doAfterTextChanged {
            viewModel.checkPasswordFormat(binding.passwordEditText.text.toString())
        }
    }

    private fun isLoginAllowed(): Boolean {
        return viewModel.isEmailValid.value == true &&
                viewModel.isPasswordValid.value == true
    }

    private fun checkLoggedInUser(){
        if (auth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}