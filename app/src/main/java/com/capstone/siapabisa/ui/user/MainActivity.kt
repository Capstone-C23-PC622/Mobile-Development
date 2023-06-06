package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityMainBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.dummy.jobList
import com.capstone.siapabisa.ui.user.adapter.ListJobAdapter
import com.capstone.siapabisa.ui.user.adapter.ListMessageAdapter
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: ListJobAdapter

    private val viewModel: MainViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        setupBottomNav()
        setupListJobs()

    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
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

    private fun setupListJobs(){
        val layoutMgr = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRekomendasi.layoutManager = layoutMgr
        adapter = ListJobAdapter()
        binding.rvRekomendasi.setHasFixedSize(true)
        binding.rvRekomendasi.adapter = adapter

        adapter.setData(jobList)
    }
}