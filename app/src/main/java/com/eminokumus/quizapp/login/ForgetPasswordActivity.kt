package com.eminokumus.quizapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.quizapp.MyApplication
import com.eminokumus.quizapp.R
import com.eminokumus.quizapp.databinding.ActivityForgetPasswordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import javax.inject.Inject

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var dbFirebase: DatabaseReference

    private var isEmailExists = false


    @Inject
    lateinit var viewModel: ForgetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        dbFirebase = Firebase.database.getReference("emails")

        observeEmail()
        checkEmail()
        setOnClickListeners()

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
        setBackBtnOnClickListener()
        setSendLinkBtnOnClickListener()
    }

    private fun setSendLinkBtnOnClickListener() {
        binding.sendLinkButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            var emailList  = mutableListOf<String>()

            dbFirebase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children){
                        item.getValue(String::class.java)?.let { emailList.add(it) }
                    }
                    isEmailExists = emailList.contains(email)
                    sendResetEmail()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", error.toString())
                }
            })


        }
    }

    private fun sendResetEmail() {
        if (viewModel.isEmailValid.value == true &&
            isEmailExists
        ) {
            auth.sendPasswordResetEmail(binding.emailEditText.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "Email sent",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.e("error", it.exception.toString())
                    }
                }
        } else {
            Toast.makeText(
                this@ForgetPasswordActivity,
                "This email is not exist",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setBackBtnOnClickListener() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkEmail() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
    }

//    private fun checkEmailExists(email: String){
//        var emailList  = mutableListOf<String>()
//        dbFirebase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (item in snapshot.children){
//                    item.getValue(String::class.java)?.let { emailList.add(it) }
//                }
//                println("emaillist: $emailList")
//                println("email: $email")
//                println("iscontains snapshot: ${emailList.contains(email)}")
//                isEmailExists = emailList.contains(email)
//
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("error", error.toString())
//
//            }
//        })
//    }


}