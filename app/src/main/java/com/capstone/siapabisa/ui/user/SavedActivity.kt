package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivitySavedBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.dummy.jobList
import com.capstone.siapabisa.ui.user.adapter.ListJobAdapter
import com.capstone.siapabisa.ui.user.adapter.ListMessageAdapter

class SavedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: ListJobAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        setupBottomNav()
        setupListJobs()

    }

    private fun setupBottomNav() {

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.saved

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

//                R.id.saved -> {
//                    startActivity(Intent(this, SavedActivity::class.java))
//                    true
//                }

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

    private fun setupListJobs() {
        val layoutMgr = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvJoblist.layoutManager = layoutMgr
        adapter = ListJobAdapter()
        binding.rvJoblist.setHasFixedSize(true)
        binding.rvJoblist.adapter = adapter

        adapter.setData(jobList)
    }
}