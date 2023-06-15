package com.capstone.siapabisa.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.databinding.ActivityLoginRegisterBinding

class LoginRegister : AppCompatActivity() {

    private lateinit var binding : ActivityLoginRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginPref = LoginPreferences(this)
        loginPref.clearData()

       binding. btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}