package com.capstone.siapabisa.ui.usaha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityUsahaBinding
import com.capstone.siapabisa.di.ViewModelFactory

class UsahaBiodataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsahaBinding
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
    }
}