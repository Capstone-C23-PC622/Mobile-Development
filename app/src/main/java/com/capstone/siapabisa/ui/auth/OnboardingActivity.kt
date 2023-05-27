package com.capstone.siapabisa.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}