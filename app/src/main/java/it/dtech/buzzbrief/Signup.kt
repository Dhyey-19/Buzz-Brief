package it.dtech.buzzbrief

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import it.dtech.buzzbrief.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val mobile = binding.mobileEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (name.isEmpty()) {
                binding.nameTextInputLayout.error = "Name is required"
                return@setOnClickListener
            } else {
                binding.nameTextInputLayout.error = null
            }

            if (mobile.isEmpty()) {
                binding.mobileTextInputLayout.error = "Mobile number is required"
                return@setOnClickListener
            } else {
                binding.mobileTextInputLayout.error = null
            }

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

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}
