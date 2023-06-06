package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivitySearchBinding
import com.google.android.material.navigation.NavigationBarView

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        NavigationBarView.OnItemSelectedListener { item ->
            when(item.itemId) {
//                R.id.home -> {
//                    startActivity(Intent(this, MainActivity::class.java))
//                    true
//                }

                R.id.saved -> {
                    startActivity(Intent(this, SavedActivity::class.java))
                    true
                }
                R.id.notification -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }
                R.id.akun -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}