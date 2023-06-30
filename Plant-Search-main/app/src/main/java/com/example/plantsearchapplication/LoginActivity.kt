package com.example.plantsearchapplication

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.plantsearchapplication.databinding.ActivityLoginBinding
import com.example.plantsearchapplication.databinding.DialogForgotBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()


            // Check Email & Password Fields
            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            if (user != null) {
                                if (user.isEmailVerified) {
                                    // Redirect to main activity
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // Display message and send verification email
                                    Toast.makeText(this, "Please verify your email before logging in!", Toast.LENGTH_SHORT).show()
                                    user.sendEmailVerification()
                                }
                            }
                        } else {
                            // Display error message
                            Toast.makeText(this, "The email address or password is incorrect. Please retry!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            if (email.isEmpty()){
                Toast.makeText(this, "Email is empty!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Please enter a valid email!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                Toast.makeText(this, "Password is empty!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }

        binding.forgotPassword.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_forgot, null)

            val userEmail = view.findViewById<EditText>(R.id.desc)
            builder.setView(view)
            val dialog = builder.create()
            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareEmail(userEmail, dialog)

            }
            dialog.show()
        }

        binding.signupRedirectText.setOnClickListener {
            val signupIntent = Intent(this, SignupActivity::class.java)
            startActivity(signupIntent)
        }


    }

    private fun compareEmail(email: EditText, dialog: AlertDialog) {
        var hasError = false

        if (email.text.toString().isEmpty()) {
            Toast.makeText(this, "Email is empty!!!", Toast.LENGTH_SHORT).show()
            email.requestFocus()
            hasError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            Toast.makeText(this, "Please enter a valid email!!!", Toast.LENGTH_SHORT).show()
            email.requestFocus()
            hasError = true
        }

        if (!hasError) {
            firebaseAuth.sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Check your email!!!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
        }
    }
}

