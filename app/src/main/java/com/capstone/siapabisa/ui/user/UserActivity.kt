package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivitySavedBinding
import com.capstone.siapabisa.databinding.ActivityUserBinding
import com.google.android.material.navigation.NavigationBarView

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()

    }

    private fun setupBottomNav() {

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.akun

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.saved -> {
                    startActivity(Intent(this, SavedActivity::class.java))
                    true
                }

                R.id.notification -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }

//                R.id.akun -> {
//                    startActivity(Intent(this, UserActivity::class.java))
//                    true
//                }

                else -> false
            }
        }

    }
}