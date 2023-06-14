package com.capstone.siapabisa.ui.usaha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityUsahaBinding
import com.capstone.siapabisa.databinding.ActivityUsahaLokerBinding
import com.capstone.siapabisa.di.ViewModelFactory

class UsahaLokerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsahaLokerBinding
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaLokerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        setupBottomNav()
    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.my_usaha

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, UsahaMainActivity::class.java))
                    true
                }

//                R.id.my_usaha -> {
//                    startActivity(Intent(this, UsahaMainActivity::class.java))
//                    true
//                }

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