package com.capstone.siapabisa.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityLoginRegisterBinding

class LoginRegister : AppCompatActivity() {

    private lateinit var binding : ActivityLoginRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}