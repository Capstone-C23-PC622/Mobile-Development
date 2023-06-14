package com.capstone.siapabisa.ui.usaha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityUsahaBinding
import com.capstone.siapabisa.databinding.ActivityUsahaMainBinding
import com.capstone.siapabisa.di.ViewModelFactory

class UsahaMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsahaMainBinding
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        setupBottomNav()

        binding.btnAddLoker.setOnClickListener {
            startActivity(Intent(this, UsahaNewLoker::class.java))
        }

    }


    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
//                R.id.home -> {
//                    startActivity(Intent(this, UsahaActivity::class.java))
//                    true
//                }

                R.id.my_usaha -> {
                    startActivity(Intent(this, UsahaLokerActivity::class.java))
                    true
                }

                R.id.notification -> {
                    startActivity(Intent(this, UsahaNotificationActivity::class.java))
                    true
                }

                R.id.akun -> {
                    startActivity(Intent(this, UsahaActivity::class.java))
                    true
                }

                else -> false
            }
        }

    }
}