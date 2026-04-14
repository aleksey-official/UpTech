package com.system.uptech

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.system.uptech.databinding.ActivityLoginBinding


class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isBlank()){
                binding.email.error = "Введите почту"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = "Введите корректную почту"
            } else if (password.isBlank() or (password.length < 6)){
                binding.password.error = "Введите пароль, минимальной длиной 6 символов"
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful){
                            Toast.makeText(this, "Logged", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                            binding.email.text = null
                            binding.password.text = null
                        }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            Toast.makeText(this,"Logged", Toast.LENGTH_SHORT).show()
        }
    }
}