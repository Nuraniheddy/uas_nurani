package com.example.uas_nurani

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.uas_nurani.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonDaftar.setOnClickListener {
            val name : String = binding.InputNama.text.toString().trim()
            val username : String = binding.InputUserName.text.toString().trim()
            val email: String = binding.InputEmail.text.toString().trim()
            val password: String = binding.InputPassword.text.toString().trim()
            val confirmPassword: String = binding.ConfigPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.InputEmail.error = "Input Email"
                binding.InputEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.InputEmail.error = "Invalid email"
                binding.InputEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.InputPassword.error = "Password must be more than 6 characters"
                binding.InputPassword.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword){
                binding.ConfigPassword.error = "Password must be match"
                binding.ConfigPassword.requestFocus()
                return@setOnClickListener
            }
            registerUser(email, password)
        }

        binding.textMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Intent(this, ContentActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
            else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}