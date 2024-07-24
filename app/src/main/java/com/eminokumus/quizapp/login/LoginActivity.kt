package com.eminokumus.quizapp.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.eminokumus.quizapp.MainActivity
import com.eminokumus.quizapp.MyApplication
import com.eminokumus.quizapp.databinding.ActivityLoginBinding
import com.eminokumus.quizapp.signup.SignupActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeViewModel()
        setOnClickListeners()

    }

    private fun observeViewModel() {
        observeIsUserValid()
    }

    private fun observeIsUserValid() {
        viewModel.isUserValid.observe(this) { isValid ->
            if (isValid) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Email/Phone Number or password incorrect", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setOnClickListeners(){
        setSignupBtnOnClickListener()
        setRootOnClickListener()
        setLoginBtnOnClickListener()

    }

    private fun setLoginBtnOnClickListener() {
        binding.loginButton.setOnClickListener {
            viewModel.checkUser(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
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


}