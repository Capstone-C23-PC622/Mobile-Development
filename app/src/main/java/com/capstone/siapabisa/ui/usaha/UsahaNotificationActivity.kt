package com.capstone.siapabisa.ui.usaha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityUsahaNewLokerBinding
import com.capstone.siapabisa.databinding.ActivityUsahaNotificationBinding
import com.capstone.siapabisa.di.ViewModelFactory

class UsahaNotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsahaNotificationBinding
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.notification

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, UsahaMainActivity::class.java))
                    true
                }

                R.id.my_usaha -> {
                    startActivity(Intent(this, UsahaLokerActivity::class.java))
                    true
                }

//                R.id.notification -> {
//                    startActivity(Intent(this, UsahaNotificationActivity::class.java))
//                    true
//                }

                R.id.akun -> {
                    startActivity(Intent(this, UsahaActivity::class.java))
                    true
                }

                else -> false
            }
        }

    }
}