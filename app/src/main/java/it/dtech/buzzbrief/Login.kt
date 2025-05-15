package it.dtech.buzzbrief

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import it.dtech.buzzbrief.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rememberMeCheckbox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)

        rememberMeCheckbox = binding.rememberMeCheckbox
        loadSavedCredentials()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailTextInputLayout.error = "Email is required"
                return@setOnClickListener
            } else {
                binding.emailTextInputLayout.error = null
            }

            if (password.isEmpty()) {
                binding.passwordTextInputLayout.error = "Password is required"
                return@setOnClickListener
            } else {
                binding.passwordTextInputLayout.error = null
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveCredentials(email, password) // Save credentials if Remember Me is checked
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Home::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.signupText.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
            finish()
        }
    }

    private fun loadSavedCredentials() {
        val savedEmail = sharedPreferences.getString("email", null)
        val savedPassword = sharedPreferences.getString("password", null)

        if (savedEmail != null && savedPassword != null) {
            binding.emailEditText.setText(savedEmail)
            binding.passwordEditText.setText(savedPassword)
            rememberMeCheckbox.isChecked = true // Check the checkbox if credentials are loaded
        }
    }

    private fun saveCredentials(email: String, password: String) {
        if (rememberMeCheckbox.isChecked) {
            with(sharedPreferences.edit()) {
                putString("email", email)
                putString("password", password)
                apply()
            }
        } else {
            with(sharedPreferences.edit()) {
                remove("email")
                remove("password")
                apply()
            }
        }
    }
}
