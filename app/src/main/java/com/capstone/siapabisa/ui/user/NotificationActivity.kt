package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityNotificationBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.dummy.messagesList
import com.capstone.siapabisa.ui.user.adapter.ListMessageAdapter

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: ListMessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        setupBottomNav()
        setupListMessages()
    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.notification

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

//                R.id.notification -> {
//                    startActivity(Intent(this, NotificationActivity::class.java))
//                    true
//                }

                R.id.akun -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    true
                }

                else -> false
            }
        }

    }

    private fun setupListMessages() {
        val layoutMgr = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNotification.layoutManager = layoutMgr
        adapter = ListMessageAdapter()
        binding.rvNotification.setHasFixedSize(true)
        binding.rvNotification.adapter = adapter

        adapter.setData(messagesList)
    }
}

