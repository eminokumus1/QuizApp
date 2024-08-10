package com.eminokumus.quizapp.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.quizapp.MyApplication
import com.eminokumus.quizapp.databinding.ActivitySignupBinding
import com.eminokumus.quizapp.login.LoginActivity
import com.eminokumus.quizapp.vo.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import javax.inject.Inject

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var dbFirebase: DatabaseReference

    @Inject
    lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        checkFields()

        auth = Firebase.auth

        dbFirebase = Firebase.database.getReference("user")

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.root.setOnClickListener {
            hideKeyboard()
        }

        binding.signupButton.setOnClickListener {
            if (isSignupAllowed()) {
                auth.createUserWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        auth.currentUser?.let { it1 -> addUserToFirebase(it1.uid, binding.emailEditText.text.toString()) }
                        auth.signOut()
                        Toast.makeText(this, "Signup completed", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("error", it.exception.toString())
                        Toast.makeText(this, "This email is already in use", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun observeViewModel() {
        viewModel.isEmailValid.observe(this) { isValid ->
            if (isValid) {
                binding.emailField.error = null
            } else {
                binding.emailField.error = "Wrong email format"
            }
        }
        viewModel.isPasswordValid.observe(this) { isValid ->
            if (isValid) {
                binding.passwordField.error = null
            } else {
                binding.passwordField.error = "Minimum 6 characters"
            }
        }
        viewModel.isPasswordConfirm.observe(this) { isConfirmed ->
            if (isConfirmed) {
                binding.confirmPasswordField.error = null
            } else {
                binding.confirmPasswordField.error = "Doesn't match with password"
            }

        }
    }

    private fun isSignupAllowed(): Boolean {
        return viewModel.isEmailValid.value == true &&
                viewModel.isPasswordValid.value == true &&
                viewModel.isPasswordConfirm.value == true
    }

    private fun checkFields() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
        binding.passwordEditText.doAfterTextChanged {
            viewModel.checkPasswordFormat(binding.passwordEditText.text.toString())
        }
        binding.confirmPasswordEditText.doAfterTextChanged {
            viewModel.checkConfirmPassword(
                binding.confirmPasswordEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }


    private fun addUserToFirebase(userId: String, userEmail: String) {
        val user = User(userId, userEmail)
        println("userId: ${user.userId}")
        dbFirebase.child(userId).child("userData").setValue(User(userId, userEmail))
    }
}